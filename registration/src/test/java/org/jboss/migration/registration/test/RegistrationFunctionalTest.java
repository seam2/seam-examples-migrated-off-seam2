/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.migration.registration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.migration.registration.RegisterAction;
import org.jboss.migration.registration.User;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class tests registration form functionality in registration example.
 *
 * @author Marek Novotny
 *
 */
@RunAsClient
@RunWith(Arquillian.class)
public class RegistrationFunctionalTest {

	/*
	 * Creates deployment of Registration war for testing
	 */
	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "registration.war")
				.addPackage(RegisterAction.class.getPackage())
				.addPackage(User.class.getPackage())
				.addAsResource("META-INF/persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(
						new File("src/main/webapp/WEB-INF", "beans.xml"))
				.addAsWebInfResource(
						new File("src/main/webapp/WEB-INF", "faces-config.xml"))
				.addAsWebResource(new File("src/main/webapp", "index.html"))
				.addAsWebResource(new File("src/main/webapp", "register.xhtml"))
				.addAsWebResource(
						new File("src/main/webapp", "registered.xhtml"));
	}

	/*
	 * Check if Registration war is deployed fine
	 */
	@Before
	public void beforeTest() {
		browser.navigate().to(deploymentUrl);
		sleep(1000);
	}

	/*
	 * Helper for a pause
	 */
	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
		}
	}

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	// Registration form fields for testing
	@FindBy(id = "registration:username")
	WebElement username;

	@FindBy(id = "registration:name")
	WebElement name;

	@FindBy(id = "registration:password")
	WebElement password;

	@FindBy(id = "registration:register")
	WebElement register;
	
	// End of Registration form fields for testing

	@Test
	@InSequence(1)
	public void simpleRegistrationTest() {
		submitRegistrationForm("johny", "John Doe", "secretPassword");

		assertEquals("Successfully Registered New User", browser.getTitle());
		//assertTrue("After-registration page expected.", browser.getCurrentUrl()
		//		.contains("registered.jsf")); //register.jsf;jsessionid=...
		assertTrue(
				"Welcome message should contain username.",
				browser.getPageSource().
				contains("Welcome, John Doe, you are successfully registered as johny."));
	}

	@Test
	@InSequence(2)
	public void duplicateUsernameTest() {
		submitRegistrationForm("johny", "John Doe", "secretPassword");
		browser.navigate().to(deploymentUrl);
		submitRegistrationForm("johny", "John Doe	", "secretPassword");

		assertEquals("Register New User", browser.getTitle());
		assertTrue("Error message did not appear", browser.getPageSource()
				.contains("messages"));
	}

	@Test
	@InSequence(3)
	public void emptyValuesTest() {
		submitRegistrationForm("", "", "");
		assertEquals("Register New User", browser.getTitle());
	}

	/*
	 * Submits registration form 
	 */
	private void submitRegistrationForm(String userName, String s_name,
			String s_password) {
		clearFields();
		username.sendKeys(userName);
		name.sendKeys(s_name);
		password.sendKeys(s_password);
		Graphene.guardHttp(register).click();
	}

	/* 
	 * Clears all text in tested WebElement like form fields
	 */
	private void clearFields() {
		username.clear();
		name.clear();
		password.clear();
	}

}
