
package tw.gov.tpl.holdnotice_service;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for noticeResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="noticeResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pkid" type="{http://holdnotice-service.tpl.gov.tw}noticeResultPK" minOccurs="0"/>
 *         &lt;element name="descString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="joinId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="lastStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="statusTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "noticeResult", namespace = "http://holdnotice-service.tpl.gov.tw", propOrder = {
    "pkid",
    "descString",
    "joinId",
    "lastStatus",
    "result"
})
public class NoticeResult {

    protected NoticeResultPK pkid;
    protected String descString;
    protected BigInteger joinId;
    protected String lastStatus;
    protected String result;
    @XmlAttribute
    protected XMLGregorianCalendar startTime;
    @XmlAttribute
    protected XMLGregorianCalendar statusTime;

    /**
     * Gets the value of the pkid property.
     * 
     * @return
     *     possible object is
     *     {@link NoticeResultPK }
     *     
     */
    public NoticeResultPK getPkid() {
        return pkid;
    }

    /**
     * Sets the value of the pkid property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoticeResultPK }
     *     
     */
    public void setPkid(NoticeResultPK value) {
        this.pkid = value;
    }

    /**
     * Gets the value of the descString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescString() {
        return descString;
    }

    /**
     * Sets the value of the descString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescString(String value) {
        this.descString = value;
    }

    /**
     * Gets the value of the joinId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getJoinId() {
        return joinId;
    }

    /**
     * Sets the value of the joinId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setJoinId(BigInteger value) {
        this.joinId = value;
    }

    /**
     * Gets the value of the lastStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastStatus() {
        return lastStatus;
    }

    /**
     * Sets the value of the lastStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastStatus(String value) {
        this.lastStatus = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the statusTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusTime() {
        return statusTime;
    }

    /**
     * Sets the value of the statusTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusTime(XMLGregorianCalendar value) {
        this.statusTime = value;
    }

}
