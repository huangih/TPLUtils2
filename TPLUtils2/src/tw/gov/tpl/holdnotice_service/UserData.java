
package tw.gov.tpl.holdnotice_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}cancelExpiredHold" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}holdNotice" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="homeNum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mailAddr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mobileNum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="noticeType" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" />
 *       &lt;attribute name="officeNum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uid" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="userKey" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userData", namespace = "http://holdnotice-service.tpl.gov.tw", propOrder = {
    "cancelExpiredHold",
    "holdNotice"
})
public class UserData {

    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected List<CancelExpiredHold> cancelExpiredHold;
    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected List<HoldNotice> holdNotice;
    @XmlAttribute
    protected String homeNum;
    @XmlAttribute
    protected String mailAddr;
    @XmlAttribute
    protected String mobileNum;
    @XmlAttribute
    protected String name;
    @XmlAttribute(required = true)
    protected int noticeType;
    @XmlAttribute
    protected String officeNum;
    @XmlAttribute(required = true)
    protected String uid;
    @XmlAttribute(required = true)
    protected long userKey;

    /**
     * Gets the value of the cancelExpiredHold property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cancelExpiredHold property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCancelExpiredHold().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CancelExpiredHold }
     * 
     * 
     */
    public List<CancelExpiredHold> getCancelExpiredHold() {
        if (cancelExpiredHold == null) {
            cancelExpiredHold = new ArrayList<CancelExpiredHold>();
        }
        return this.cancelExpiredHold;
    }

    /**
     * Gets the value of the holdNotice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the holdNotice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHoldNotice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HoldNotice }
     * 
     * 
     */
    public List<HoldNotice> getHoldNotice() {
        if (holdNotice == null) {
            holdNotice = new ArrayList<HoldNotice>();
        }
        return this.holdNotice;
    }

    /**
     * Gets the value of the homeNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeNum() {
        return homeNum;
    }

    /**
     * Sets the value of the homeNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeNum(String value) {
        this.homeNum = value;
    }

    /**
     * Gets the value of the mailAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAddr() {
        return mailAddr;
    }

    /**
     * Sets the value of the mailAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAddr(String value) {
        this.mailAddr = value;
    }

    /**
     * Gets the value of the mobileNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileNum() {
        return mobileNum;
    }

    /**
     * Sets the value of the mobileNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileNum(String value) {
        this.mobileNum = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the noticeType property.
     * 
     */
    public int getNoticeType() {
        return noticeType;
    }

    /**
     * Sets the value of the noticeType property.
     * 
     */
    public void setNoticeType(int value) {
        this.noticeType = value;
    }

    /**
     * Gets the value of the officeNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeNum() {
        return officeNum;
    }

    /**
     * Sets the value of the officeNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeNum(String value) {
        this.officeNum = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the userKey property.
     * 
     */
    public long getUserKey() {
        return userKey;
    }

    /**
     * Sets the value of the userKey property.
     * 
     */
    public void setUserKey(long value) {
        this.userKey = value;
    }

}
