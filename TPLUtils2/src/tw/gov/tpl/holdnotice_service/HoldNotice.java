
package tw.gov.tpl.holdnotice_service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for holdNotice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="holdNotice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noticeProp" type="{http://www.w3.org/2001/XMLSchema}IDREF" minOccurs="0"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}userData"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}BranchData"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}catTitle"/>
 *       &lt;/sequence>
 *       &lt;attribute name="callnum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cancelNid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="createDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="cusSeqNum" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="expDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="hStatus" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="holdId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="itemId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "holdNotice", namespace = "http://holdnotice-service.tpl.gov.tw", propOrder = {
    "noticeProp",
    "userData",
    "branchData",
    "catTitle"
})
public class HoldNotice {

    @XmlIDREF
    protected Object noticeProp;
    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected UserData userData;
    @XmlElement(name = "BranchData", namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected BrnName branchData;
    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected CatTitle catTitle;
    @XmlAttribute
    protected String callnum;
    @XmlAttribute
    protected String cancelNid;
    @XmlAttribute
    protected XMLGregorianCalendar createDatetime;
    @XmlAttribute(required = true)
    protected int cusSeqNum;
    @XmlAttribute
    protected XMLGregorianCalendar expDate;
    @XmlAttribute(required = true)
    protected int hStatus;
    @XmlAttribute(required = true)
    protected int holdId;
    @XmlAttribute
    protected String itemId;

    /**
     * Gets the value of the noticeProp property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNoticeProp() {
        return noticeProp;
    }

    /**
     * Sets the value of the noticeProp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNoticeProp(Object value) {
        this.noticeProp = value;
    }

    /**
     * Gets the value of the userData property.
     * 
     * @return
     *     possible object is
     *     {@link UserData }
     *     
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Sets the value of the userData property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserData }
     *     
     */
    public void setUserData(UserData value) {
        this.userData = value;
    }

    /**
     * Gets the value of the branchData property.
     * 
     * @return
     *     possible object is
     *     {@link BrnName }
     *     
     */
    public BrnName getBranchData() {
        return branchData;
    }

    /**
     * Sets the value of the branchData property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrnName }
     *     
     */
    public void setBranchData(BrnName value) {
        this.branchData = value;
    }

    /**
     * Gets the value of the catTitle property.
     * 
     * @return
     *     possible object is
     *     {@link CatTitle }
     *     
     */
    public CatTitle getCatTitle() {
        return catTitle;
    }

    /**
     * Sets the value of the catTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatTitle }
     *     
     */
    public void setCatTitle(CatTitle value) {
        this.catTitle = value;
    }

    /**
     * Gets the value of the callnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallnum() {
        return callnum;
    }

    /**
     * Sets the value of the callnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallnum(String value) {
        this.callnum = value;
    }

    /**
     * Gets the value of the cancelNid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelNid() {
        return cancelNid;
    }

    /**
     * Sets the value of the cancelNid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelNid(String value) {
        this.cancelNid = value;
    }

    /**
     * Gets the value of the createDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDatetime() {
        return createDatetime;
    }

    /**
     * Sets the value of the createDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDatetime(XMLGregorianCalendar value) {
        this.createDatetime = value;
    }

    /**
     * Gets the value of the cusSeqNum property.
     * 
     */
    public int getCusSeqNum() {
        return cusSeqNum;
    }

    /**
     * Sets the value of the cusSeqNum property.
     * 
     */
    public void setCusSeqNum(int value) {
        this.cusSeqNum = value;
    }

    /**
     * Gets the value of the expDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpDate() {
        return expDate;
    }

    /**
     * Sets the value of the expDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpDate(XMLGregorianCalendar value) {
        this.expDate = value;
    }

    /**
     * Gets the value of the hStatus property.
     * 
     */
    public int getHStatus() {
        return hStatus;
    }

    /**
     * Sets the value of the hStatus property.
     * 
     */
    public void setHStatus(int value) {
        this.hStatus = value;
    }

    /**
     * Gets the value of the holdId property.
     * 
     */
    public int getHoldId() {
        return holdId;
    }

    /**
     * Sets the value of the holdId property.
     * 
     */
    public void setHoldId(int value) {
        this.holdId = value;
    }

    /**
     * Gets the value of the itemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Sets the value of the itemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemId(String value) {
        this.itemId = value;
    }

}
