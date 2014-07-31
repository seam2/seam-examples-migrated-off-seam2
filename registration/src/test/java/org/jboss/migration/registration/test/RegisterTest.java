//$Id: RegisterTest.java 10428 2009-04-15 21:54:38Z norman.richards@jboss.com $
package org.jboss.migration.registration.test;

import java.io.File;

import javax.faces.component.UIInput;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.migration.registration.RegisterAction;
import org.jboss.migration.registration.User;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(Arquillian.class)
public class RegisterTest {
    @Deployment(name = "RegisterTest")
    @OverProtocol("Servlet 3.0")
    public static Archive<?> createDeployment() {

	/*File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
		.importCompileAndRuntimeDependencies().resolve()
		.withTransitivity().asFile();*/

	return ShrinkWrap.create(WebArchive.class, "registration.war")
		.addPackage(RegisterAction.class.getPackage())
		.addPackage(User.class.getPackage())
		.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
		//.addAsWebInfResource("WEB-INF/beans.xml","WEB-INF/beans.xml")
		.addAsWebInfResource(new File("src/main/webapp/WEB-INF","faces-config.xml"))
		.addAsWebResource(new File("src/main/webapp","index.html"))
		.addAsWebResource(new File("src/main/webapp","register.xhtml"))
		.addAsWebResource(new File("src/main/webapp","registered.xhtml"));//.addAsLibraries(libs);
    }
    
    //@Test
    public void testLogin() throws Exception {
    	RegisterAction register = new RegisterAction();
    	UIInput userUIInput = new UIInput();
    	userUIInput.setValue("testuser");
    	register.setUsernameInput(userUIInput);
    	Assert.assertEquals("User is not registered", "/registered.xhtml", register.register()); ;

	/*
	 * new FacesRequest("/register.xhtml") {
	 * 
	 * @Override protected void processValidations() throws Exception {
	 * validateValue("#{user.username}", "1ovthafew");
	 * validateValue("#{user.name}", "Gavin King");
	 * validateValue("#{user.password}", "secret"); assert
	 * !isValidationFailure(); }
	 * 
	 * @Override protected void updateModelValues() throws Exception {
	 * setValue("#{user.username}", "1ovthafew"); setValue("#{user.name}",
	 * "Gavin King"); setValue("#{user.password}", "secret"); }
	 * 
	 * @Override protected void invokeApplication() { assert
	 * invokeMethod("#{register.register}").equals("/registered.xhtml");
	 * setOutcome("/registered.xhtml"); }
	 * 
	 * @Override protected void afterRequest() { assert
	 * isInvokeApplicationComplete(); assert !isRenderResponseBegun(); }
	 * 
	 * }.run();
	 * 
	 * new NonFacesRequest("/registered.xhtml") {
	 * 
	 * @Override protected void renderResponse() { assert
	 * getValue("#{user.username}").equals("1ovthafew"); assert
	 * getValue("#{user.password}").equals("secret"); assert
	 * getValue("#{user.name}").equals("Gavin King"); }
	 * 
	 * }.run();
	 * 
	 * new FacesRequest("/register.xhtml") {
	 * 
	 * @Override protected void processValidations() throws Exception {
	 * validateValue("#{user.username}", "1ovthafew");
	 * validateValue("#{user.name}", "Gavin A King");
	 * validateValue("#{user.password}", "password"); }
	 * 
	 * @Override protected void updateModelValues() throws Exception {
	 * setValue("#{user.username}", "1ovthafew"); setValue("#{user.name}",
	 * "Gavin A King"); setValue("#{user.password}", "password"); }
	 * 
	 * @Override protected void invokeApplication() { assert
	 * invokeMethod("#{register.register}") == null; }
	 * 
	 * @Override protected void renderResponse() throws Exception { assert
	 * FacesContext.getCurrentInstance().getMessages().hasNext(); }
	 * 
	 * @Override protected void afterRequest() { assert
	 * isInvokeApplicationComplete(); assert isRenderResponseComplete(); }
	 * 
	 * }.run();
	 */

    }

}
