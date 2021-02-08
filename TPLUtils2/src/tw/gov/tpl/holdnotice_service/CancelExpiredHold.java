
package tw.gov.tpl.holdnotice_service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cancelExpiredHold complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cancelExpiredHold">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userData" type="{http://holdnotice-service.tpl.gov.tw}userData" minOccurs="0"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}holdNotice"/>
 *       &lt;/sequence>
 *       &lt;attribute name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="encount" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="holdId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="retouchDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancelExpiredHold", namespace = "http://holdnotice-service.tpl.gov.tw", propOrder = {
    "userData",
    "holdNotice"
})
public class CancelExpiredHold {

    protected UserData userData;
    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected HoldNotice holdNotice;
    @XmlAttribute
    protected XMLGregorianCalendar createDate;
    @XmlAttribute(required = true)
    protected int encount;
    @XmlAttribute(required = true)
    protected int holdId;
    @XmlAttribute
    protected XMLGregorianCalendar retouchDatetime;

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
     * Gets the value of the holdNotice property.
     * 
     * @return
     *     possible object is
     *     {@link HoldNotice }
     *     
     */
    public HoldNotice getHoldNotice() {
        return holdNotice;
    }

    /**
     * Sets the value of the holdNotice property.
     * 
     * @param value
     *     allowed object is
     *     {@link HoldNotice }
     *     
     */
    public void setHoldNotice(HoldNotice value) {
        this.holdNotice = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the encount property.
     * 
     */
    public int getEncount() {
        return encount;
    }

    /**
     * Sets the value of the encount property.
     * 
     */
    public void setEncount(int value) {
        this.encount = value;
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
     * Gets the value of the retouchDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetouchDatetime() {
        return retouchDatetime;
    }

    /**
     * Sets the value of the retouchDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetouchDatetime(XMLGregorianCalendar value) {
        this.retouchDatetime = value;
    }

}
