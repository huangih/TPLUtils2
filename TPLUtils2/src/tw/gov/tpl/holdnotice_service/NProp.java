
package tw.gov.tpl.holdnotice_service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for nProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brnName" type="{http://holdnotice-service.tpl.gov.tw}brnName"/>
 *         &lt;element name="userData" type="{http://holdnotice-service.tpl.gov.tw}userData" minOccurs="0"/>
 *         &lt;element ref="{http://holdnotice-service.tpl.gov.tw}holdNotice" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="number" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nProp", namespace = "http://holdnotice-service.tpl.gov.tw", propOrder = {
    "brnName",
    "userData",
    "holdNotice"
})
public class NProp {

    @XmlElement(required = true)
    protected BrnName brnName;
    protected UserData userData;
    @XmlElement(namespace = "http://holdnotice-service.tpl.gov.tw", required = true)
    protected List<HoldNotice> holdNotice;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;
    @XmlAttribute(required = true)
    protected int number;
    @XmlAttribute
    protected String type;

    /**
     * Gets the value of the brnName property.
     * 
     * @return
     *     possible object is
     *     {@link BrnName }
     *     
     */
    public BrnName getBrnName() {
        return brnName;
    }

    /**
     * Sets the value of the brnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link BrnName }
     *     
     */
    public void setBrnName(BrnName value) {
        this.brnName = value;
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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the number property.
     * 
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     */
    public void setNumber(int value) {
        this.number = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
