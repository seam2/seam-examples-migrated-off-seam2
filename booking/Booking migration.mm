<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1407328260483" ID="ID_1078928369" MODIFIED="1407332329803" TEXT="Booking migration">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Migration steps required to run the same application logic/features without Seam 2.3 framework
    </p>
  </body>
</html></richcontent>
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1407328280401" ID="ID_733805579" MODIFIED="1407328618232" POSITION="right" TEXT="Remove jboss-seam* dependencies from pom.xml files"/>
<node CREATED="1407328365384" ID="ID_7085044" MODIFIED="1407328398624" POSITION="right" TEXT="Change groupId of submodules to org.jboss.seam.examples.migrated">
<font ITALIC="true" NAME="SansSerif" SIZE="12"/>
</node>
<node CREATED="1407328306278" ID="ID_698237542" MODIFIED="1407328601415" POSITION="right" TEXT="EAR submodule migration">
<node CREATED="1407328409024" ID="ID_381711556" MODIFIED="1407328594689" TEXT="Remove jboss-deployment-structure.xml file"/>
</node>
<node CREATED="1407328321542" ID="ID_1705742887" MODIFIED="1407328347066" POSITION="right" TEXT="EJB submodule migration">
<node CREATED="1407329324603" ID="ID_1346261839" MODIFIED="1407329331691" TEXT="Remove ejb-jar.xml"/>
<node CREATED="1407329438047" ID="ID_513496973" MODIFIED="1407329443459" TEXT="Remove seam.properties"/>
<node CREATED="1407329332335" ID="ID_1341315571" MODIFIED="1407329349604" TEXT="Replace Seam annotations">
<node CREATED="1407329353554" ID="ID_778351178" MODIFIED="1407329368140" TEXT="@In =&gt; @Inject"/>
<node CREATED="1407329368655" ID="ID_1900204295" MODIFIED="1407329379756" TEXT="Remove @Out"/>
<node CREATED="1407329381098" ID="ID_839909723" MODIFIED="1407329403508" TEXT="@Name =&gt; @Named"/>
<node CREATED="1407330104602" ID="ID_1379641554" MODIFIED="1412084130398" TEXT="@Logger =&gt; use JUL or SLF4j e.g private static final Logger LOG = Logger.getLogger(HotelBookingAction.class.getName());"/>
<node CREATED="1407330118538" ID="ID_1553111132" LINK="../../seam2-migration-document/scopes.adoc" MODIFIED="1407408449596" TEXT="@Scope =&gt; ? table mapping??"/>
<node CREATED="1407330303553" ID="ID_1630217947" MODIFIED="1407330308381" TEXT="@Restrict =&gt; ??"/>
<node CREATED="1407330334057" ID="ID_1226420391" MODIFIED="1407756578724" TEXT="@Begin =&gt; call Conversation.begin() instead of annotation"/>
<node CREATED="1407330341442" ID="ID_967987775" MODIFIED="1407756590612" TEXT="@End  =&gt; call Conversation.end()  instead of annotation"/>
<node CREATED="1407330344857" ID="ID_1988205075" MODIFIED="1407407995143" TEXT="@Factory =&gt; @Produces"/>
<node CREATED="1407407996422" ID="ID_1918223903" LINK="https://github.com/seam/migration/wiki/Events#events" MODIFIED="1412084620180" TEXT="@Observer =&gt; @Observe"/>
<node CREATED="1407408596734" ID="ID_176045476" LINK="https://github.com/seam/migration/wiki/Special-Injections#datamodel--datamodel-datamodelselection-datamodelselectionindex" MODIFIED="1412084567163" TEXT="@DataModel, @DataModelSelectionm @DataModelSelectionIndex"/>
<node CREATED="1407409043452" ID="ID_1062051908" LINK="https://github.com/seam/migration/wiki/Special-Injections#logger" MODIFIED="1412084584690" TEXT="@Logger"/>
<node CREATED="1412084251243" ID="ID_1695371698" MODIFIED="1412084291265" TEXT="Events -&gt; create special BookingEvent for raising custom events"/>
</node>
<node CREATED="1407330021932" ID="ID_728415440" MODIFIED="1407330039783" TEXT="Replace used Seam components">
<node CREATED="1407330044302" ID="ID_1887840997" MODIFIED="1407330092791" TEXT="org.jboss.seam.faces.FacesMessages"/>
<node CREATED="1407330094459" ID="ID_1715841803" MODIFIED="1407332231638" TEXT="security:identity authenticate-method=&quot;#{authenticator.authenticate}&quot;"/>
<node CREATED="1407332235789" ID="ID_1115782946" MODIFIED="1407332235789" TEXT=""/>
</node>
<node CREATED="1407331029262" ID="ID_1664141477" MODIFIED="1407331067511" TEXT="Create beans.xml file in the META-INF folder of the EJB module"/>
</node>
<node CREATED="1407328329080" ID="ID_1304435562" MODIFIED="1407331456901" POSITION="right" TEXT="WEB submodule migration">
<node CREATED="1407330219842" ID="ID_910007564" MODIFIED="1407330247227" TEXT="Remove from web.xml all seam servlet, listeners or filters"/>
<node CREATED="1407330206547" ID="ID_1186454958" MODIFIED="1407330217126" TEXT="Remove components.xml"/>
<node CREATED="1407331029262" ID="ID_194741485" MODIFIED="1407331451335" TEXT="Create beans.xml file in the WEB-INF folder of the WAR module"/>
<node CREATED="1407331473761" ID="ID_1469445493" MODIFIED="1407331490030" TEXT="??? Move pages.xml to faces-config,xml ????"/>
<node CREATED="1407331673560" ID="ID_1067372228" MODIFIED="1407331686096" TEXT="Remove xmlns:s=&quot;http://jboss.org/schema/seam/taglib&quot; from JSF templates"/>
<node CREATED="1407331694473" ID="ID_1861764987" MODIFIED="1407331708221" TEXT="Remove Seam UI tags with JSF UI tags">
<node CREATED="1407331712090" ID="ID_1326076557" MODIFIED="1412077904306" TEXT="s:link =&gt; h:link"/>
<node CREATED="1407331726433" ID="ID_886188805" MODIFIED="1412077885616" TEXT="s:button =&gt; h:commandButton"/>
<node CREATED="1407331741970" ID="ID_472087319" MODIFIED="1412077870900" TEXT="s:decorate =&gt; ui:decorate"/>
<node CREATED="1407331771344" ID="ID_1729594045" MODIFIED="1412077862087" TEXT="s:message =&gt; h:messages"/>
<node CREATED="1407331791511" ID="ID_16294588" MODIFIED="1407331869956" TEXT="s:convertDateTime =&gt; &lt;f:convertDateTime type=&quot;time&quot; pattern=&quot;kk:mm:ss&quot;/&gt;"/>
<node CREATED="1407332086560" ID="ID_61792275" MODIFIED="1412078005419" TEXT="s:label =&gt; h:outputLabel"/>
<node CREATED="1407332108597" ID="ID_1348003605" MODIFIED="1412078020337" TEXT="s:span =&gt; h:outputText"/>
<node CREATED="1407332116894" ID="ID_1219241017" MODIFIED="1412078105888" TEXT="s:validateAll =&gt; not necessary, JSF default bean validator enabled"/>
</node>
</node>
<node CREATED="1407332554838" ID="ID_70309368" MODIFIED="1407332714284" POSITION="left" TEXT="Final status">
<font BOLD="true" NAME="SansSerif" SIZE="12"/>
<node CREATED="1407332569101" ID="ID_1176979141" MODIFIED="1407332583115" TEXT="Example structure"/>
<node CREATED="1407332583770" ID="ID_1729435498" MODIFIED="1407332589463" TEXT="Features">
<node CREATED="1407332609461" ID="ID_200513301" MODIFIED="1407332616856" TEXT="EAR deployment"/>
<node CREATED="1407332617498" ID="ID_1432518955" MODIFIED="1407332642968" TEXT="Testing with Arquillian/Drone/Graphene"/>
<node CREATED="1407332645282" ID="ID_952963993" MODIFIED="1407332657513" TEXT="Additional dependencies"/>
</node>
<node CREATED="1407332591285" ID="ID_800529093" MODIFIED="1407332703551" TEXT="Issues">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Everything which is not migrated or only partially migrated
    </p>
  </body>
</html></richcontent>
</node>
</node>
</node>
</map>
