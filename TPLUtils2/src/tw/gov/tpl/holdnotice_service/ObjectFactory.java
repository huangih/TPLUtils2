
package tw.gov.tpl.holdnotice_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the tw.gov.tpl.holdnotice_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetNoPrivDaysResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getNoPrivDaysResponse");
    private final static QName _ChkAccessNetClientResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chkAccessNetClientResponse");
    private final static QName _GetNotChkOEHoldIds_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getNotChkOEHoldIds");
    private final static QName _NoticeProp_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "noticeProp");
    private final static QName _GetUserAllCancelHoldsResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getUserAllCancelHoldsResponse");
    private final static QName _CancelHold_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "cancelHold");
    private final static QName _GetAllBrnResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getAllBrnResponse");
    private final static QName _NoticeResult_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "noticeResult");
    private final static QName _UserData_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "userData");
    private final static QName _ChknewHoldNotice_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chknewHoldNotice");
    private final static QName _ChgOEHoldsStatus_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chgOEHoldsStatus");
    private final static QName _BranchData_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "BranchData");
    private final static QName _GetExpandAvailHoldDateResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getExpandAvailHoldDateResponse");
    private final static QName _SendReceiveItemID_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "sendReceiveItemID");
    private final static QName _GetResultsWithHoldId_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getResultsWithHoldId");
    private final static QName _CheckNewVersion_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "checkNewVersion");
    private final static QName _ChkAccessNetClient_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chkAccessNetClient");
    private final static QName _GetNoPrivDays_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getNoPrivDays");
    private final static QName _GetUserAllCancelHolds_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getUserAllCancelHolds");
    private final static QName _CancelHoldResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "cancelHoldResponse");
    private final static QName _GetNotChkOEHoldIdsResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getNotChkOEHoldIdsResponse");
    private final static QName _HoldNotice_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "holdNotice");
    private final static QName _ChknewHoldNoticeResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chknewHoldNoticeResponse");
    private final static QName _GetAllBrn_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getAllBrn");
    private final static QName _ChgOEHoldsStatusResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "chgOEHoldsStatusResponse");
    private final static QName _GetExpandAvailHoldDate_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getExpandAvailHoldDate");
    private final static QName _SendReceiveItemIDResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "sendReceiveItemIDResponse");
    private final static QName _CancelExpiredHold_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "cancelExpiredHold");
    private final static QName _CatTitle_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "catTitle");
    private final static QName _CheckNewVersionResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "checkNewVersionResponse");
    private final static QName _GetResultsWithHoldIdResponse_QNAME = new QName("http://holdnotice-service.tpl.gov.tw", "getResultsWithHoldIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: tw.gov.tpl.holdnotice_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetNotChkOEHoldIdsResponse }
     * 
     */
    public GetNotChkOEHoldIdsResponse createGetNotChkOEHoldIdsResponse() {
        return new GetNotChkOEHoldIdsResponse();
    }

    /**
     * Create an instance of {@link GetAllBrnResponse }
     * 
     */
    public GetAllBrnResponse createGetAllBrnResponse() {
        return new GetAllBrnResponse();
    }

    /**
     * Create an instance of {@link ChkAccessNetClientResponse }
     * 
     */
    public ChkAccessNetClientResponse createChkAccessNetClientResponse() {
        return new ChkAccessNetClientResponse();
    }

    /**
     * Create an instance of {@link GetAllBrn }
     * 
     */
    public GetAllBrn createGetAllBrn() {
        return new GetAllBrn();
    }

    /**
     * Create an instance of {@link CheckNewVersionResponse }
     * 
     */
    public CheckNewVersionResponse createCheckNewVersionResponse() {
        return new CheckNewVersionResponse();
    }

    /**
     * Create an instance of {@link GetNoPrivDaysResponse }
     * 
     */
    public GetNoPrivDaysResponse createGetNoPrivDaysResponse() {
        return new GetNoPrivDaysResponse();
    }

    /**
     * Create an instance of {@link UserHoldPriv }
     * 
     */
    public UserHoldPriv createUserHoldPriv() {
        return new UserHoldPriv();
    }

    /**
     * Create an instance of {@link GetUserAllCancelHoldsResponse }
     * 
     */
    public GetUserAllCancelHoldsResponse createGetUserAllCancelHoldsResponse() {
        return new GetUserAllCancelHoldsResponse();
    }

    /**
     * Create an instance of {@link NProp }
     * 
     */
    public NProp createNProp() {
        return new NProp();
    }

    /**
     * Create an instance of {@link ChgOEHoldsStatusResponse }
     * 
     */
    public ChgOEHoldsStatusResponse createChgOEHoldsStatusResponse() {
        return new ChgOEHoldsStatusResponse();
    }

    /**
     * Create an instance of {@link GetResultsWithHoldId }
     * 
     */
    public GetResultsWithHoldId createGetResultsWithHoldId() {
        return new GetResultsWithHoldId();
    }

    /**
     * Create an instance of {@link GetExpandAvailHoldDateResponse }
     * 
     */
    public GetExpandAvailHoldDateResponse createGetExpandAvailHoldDateResponse() {
        return new GetExpandAvailHoldDateResponse();
    }

    /**
     * Create an instance of {@link GetNotChkOEHoldIds }
     * 
     */
    public GetNotChkOEHoldIds createGetNotChkOEHoldIds() {
        return new GetNotChkOEHoldIds();
    }

    /**
     * Create an instance of {@link GetNoPrivDays }
     * 
     */
    public GetNoPrivDays createGetNoPrivDays() {
        return new GetNoPrivDays();
    }

    /**
     * Create an instance of {@link CancelHoldResponse }
     * 
     */
    public CancelHoldResponse createCancelHoldResponse() {
        return new CancelHoldResponse();
    }

    /**
     * Create an instance of {@link ChknewHoldNotice }
     * 
     */
    public ChknewHoldNotice createChknewHoldNotice() {
        return new ChknewHoldNotice();
    }

    /**
     * Create an instance of {@link ChkAccessNetClient }
     * 
     */
    public ChkAccessNetClient createChkAccessNetClient() {
        return new ChkAccessNetClient();
    }

    /**
     * Create an instance of {@link CancelExpiredHold }
     * 
     */
    public CancelExpiredHold createCancelExpiredHold() {
        return new CancelExpiredHold();
    }

    /**
     * Create an instance of {@link BrnName }
     * 
     */
    public BrnName createBrnName() {
        return new BrnName();
    }

    /**
     * Create an instance of {@link NoticeResult }
     * 
     */
    public NoticeResult createNoticeResult() {
        return new NoticeResult();
    }

    /**
     * Create an instance of {@link SendReceiveItemIDResponse }
     * 
     */
    public SendReceiveItemIDResponse createSendReceiveItemIDResponse() {
        return new SendReceiveItemIDResponse();
    }

    /**
     * Create an instance of {@link CatTitle }
     * 
     */
    public CatTitle createCatTitle() {
        return new CatTitle();
    }

    /**
     * Create an instance of {@link NoticeResultPK }
     * 
     */
    public NoticeResultPK createNoticeResultPK() {
        return new NoticeResultPK();
    }

    /**
     * Create an instance of {@link UserData }
     * 
     */
    public UserData createUserData() {
        return new UserData();
    }

    /**
     * Create an instance of {@link CheckNewVersion }
     * 
     */
    public CheckNewVersion createCheckNewVersion() {
        return new CheckNewVersion();
    }

    /**
     * Create an instance of {@link CancelHold }
     * 
     */
    public CancelHold createCancelHold() {
        return new CancelHold();
    }

    /**
     * Create an instance of {@link GetUserAllCancelHolds }
     * 
     */
    public GetUserAllCancelHolds createGetUserAllCancelHolds() {
        return new GetUserAllCancelHolds();
    }

    /**
     * Create an instance of {@link GetExpandAvailHoldDate }
     * 
     */
    public GetExpandAvailHoldDate createGetExpandAvailHoldDate() {
        return new GetExpandAvailHoldDate();
    }

    /**
     * Create an instance of {@link GetResultsWithHoldIdResponse }
     * 
     */
    public GetResultsWithHoldIdResponse createGetResultsWithHoldIdResponse() {
        return new GetResultsWithHoldIdResponse();
    }

    /**
     * Create an instance of {@link ChgOEHoldsStatus }
     * 
     */
    public ChgOEHoldsStatus createChgOEHoldsStatus() {
        return new ChgOEHoldsStatus();
    }

    /**
     * Create an instance of {@link ChknewHoldNoticeResponse }
     * 
     */
    public ChknewHoldNoticeResponse createChknewHoldNoticeResponse() {
        return new ChknewHoldNoticeResponse();
    }

    /**
     * Create an instance of {@link SendReceiveItemID }
     * 
     */
    public SendReceiveItemID createSendReceiveItemID() {
        return new SendReceiveItemID();
    }

    /**
     * Create an instance of {@link HoldNotice }
     * 
     */
    public HoldNotice createHoldNotice() {
        return new HoldNotice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNoPrivDaysResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getNoPrivDaysResponse")
    public JAXBElement<GetNoPrivDaysResponse> createGetNoPrivDaysResponse(GetNoPrivDaysResponse value) {
        return new JAXBElement<GetNoPrivDaysResponse>(_GetNoPrivDaysResponse_QNAME, GetNoPrivDaysResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChkAccessNetClientResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chkAccessNetClientResponse")
    public JAXBElement<ChkAccessNetClientResponse> createChkAccessNetClientResponse(ChkAccessNetClientResponse value) {
        return new JAXBElement<ChkAccessNetClientResponse>(_ChkAccessNetClientResponse_QNAME, ChkAccessNetClientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotChkOEHoldIds }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getNotChkOEHoldIds")
    public JAXBElement<GetNotChkOEHoldIds> createGetNotChkOEHoldIds(GetNotChkOEHoldIds value) {
        return new JAXBElement<GetNotChkOEHoldIds>(_GetNotChkOEHoldIds_QNAME, GetNotChkOEHoldIds.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NProp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "noticeProp")
    public JAXBElement<NProp> createNoticeProp(NProp value) {
        return new JAXBElement<NProp>(_NoticeProp_QNAME, NProp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserAllCancelHoldsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getUserAllCancelHoldsResponse")
    public JAXBElement<GetUserAllCancelHoldsResponse> createGetUserAllCancelHoldsResponse(GetUserAllCancelHoldsResponse value) {
        return new JAXBElement<GetUserAllCancelHoldsResponse>(_GetUserAllCancelHoldsResponse_QNAME, GetUserAllCancelHoldsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHold }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "cancelHold")
    public JAXBElement<CancelHold> createCancelHold(CancelHold value) {
        return new JAXBElement<CancelHold>(_CancelHold_QNAME, CancelHold.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBrnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getAllBrnResponse")
    public JAXBElement<GetAllBrnResponse> createGetAllBrnResponse(GetAllBrnResponse value) {
        return new JAXBElement<GetAllBrnResponse>(_GetAllBrnResponse_QNAME, GetAllBrnResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoticeResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "noticeResult")
    public JAXBElement<NoticeResult> createNoticeResult(NoticeResult value) {
        return new JAXBElement<NoticeResult>(_NoticeResult_QNAME, NoticeResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "userData")
    public JAXBElement<UserData> createUserData(UserData value) {
        return new JAXBElement<UserData>(_UserData_QNAME, UserData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChknewHoldNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chknewHoldNotice")
    public JAXBElement<ChknewHoldNotice> createChknewHoldNotice(ChknewHoldNotice value) {
        return new JAXBElement<ChknewHoldNotice>(_ChknewHoldNotice_QNAME, ChknewHoldNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChgOEHoldsStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chgOEHoldsStatus")
    public JAXBElement<ChgOEHoldsStatus> createChgOEHoldsStatus(ChgOEHoldsStatus value) {
        return new JAXBElement<ChgOEHoldsStatus>(_ChgOEHoldsStatus_QNAME, ChgOEHoldsStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BrnName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "BranchData")
    public JAXBElement<BrnName> createBranchData(BrnName value) {
        return new JAXBElement<BrnName>(_BranchData_QNAME, BrnName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExpandAvailHoldDateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getExpandAvailHoldDateResponse")
    public JAXBElement<GetExpandAvailHoldDateResponse> createGetExpandAvailHoldDateResponse(GetExpandAvailHoldDateResponse value) {
        return new JAXBElement<GetExpandAvailHoldDateResponse>(_GetExpandAvailHoldDateResponse_QNAME, GetExpandAvailHoldDateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendReceiveItemID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "sendReceiveItemID")
    public JAXBElement<SendReceiveItemID> createSendReceiveItemID(SendReceiveItemID value) {
        return new JAXBElement<SendReceiveItemID>(_SendReceiveItemID_QNAME, SendReceiveItemID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultsWithHoldId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getResultsWithHoldId")
    public JAXBElement<GetResultsWithHoldId> createGetResultsWithHoldId(GetResultsWithHoldId value) {
        return new JAXBElement<GetResultsWithHoldId>(_GetResultsWithHoldId_QNAME, GetResultsWithHoldId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckNewVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "checkNewVersion")
    public JAXBElement<CheckNewVersion> createCheckNewVersion(CheckNewVersion value) {
        return new JAXBElement<CheckNewVersion>(_CheckNewVersion_QNAME, CheckNewVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChkAccessNetClient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chkAccessNetClient")
    public JAXBElement<ChkAccessNetClient> createChkAccessNetClient(ChkAccessNetClient value) {
        return new JAXBElement<ChkAccessNetClient>(_ChkAccessNetClient_QNAME, ChkAccessNetClient.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNoPrivDays }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getNoPrivDays")
    public JAXBElement<GetNoPrivDays> createGetNoPrivDays(GetNoPrivDays value) {
        return new JAXBElement<GetNoPrivDays>(_GetNoPrivDays_QNAME, GetNoPrivDays.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserAllCancelHolds }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getUserAllCancelHolds")
    public JAXBElement<GetUserAllCancelHolds> createGetUserAllCancelHolds(GetUserAllCancelHolds value) {
        return new JAXBElement<GetUserAllCancelHolds>(_GetUserAllCancelHolds_QNAME, GetUserAllCancelHolds.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHoldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "cancelHoldResponse")
    public JAXBElement<CancelHoldResponse> createCancelHoldResponse(CancelHoldResponse value) {
        return new JAXBElement<CancelHoldResponse>(_CancelHoldResponse_QNAME, CancelHoldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotChkOEHoldIdsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getNotChkOEHoldIdsResponse")
    public JAXBElement<GetNotChkOEHoldIdsResponse> createGetNotChkOEHoldIdsResponse(GetNotChkOEHoldIdsResponse value) {
        return new JAXBElement<GetNotChkOEHoldIdsResponse>(_GetNotChkOEHoldIdsResponse_QNAME, GetNotChkOEHoldIdsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HoldNotice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "holdNotice")
    public JAXBElement<HoldNotice> createHoldNotice(HoldNotice value) {
        return new JAXBElement<HoldNotice>(_HoldNotice_QNAME, HoldNotice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChknewHoldNoticeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chknewHoldNoticeResponse")
    public JAXBElement<ChknewHoldNoticeResponse> createChknewHoldNoticeResponse(ChknewHoldNoticeResponse value) {
        return new JAXBElement<ChknewHoldNoticeResponse>(_ChknewHoldNoticeResponse_QNAME, ChknewHoldNoticeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllBrn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getAllBrn")
    public JAXBElement<GetAllBrn> createGetAllBrn(GetAllBrn value) {
        return new JAXBElement<GetAllBrn>(_GetAllBrn_QNAME, GetAllBrn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChgOEHoldsStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "chgOEHoldsStatusResponse")
    public JAXBElement<ChgOEHoldsStatusResponse> createChgOEHoldsStatusResponse(ChgOEHoldsStatusResponse value) {
        return new JAXBElement<ChgOEHoldsStatusResponse>(_ChgOEHoldsStatusResponse_QNAME, ChgOEHoldsStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetExpandAvailHoldDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getExpandAvailHoldDate")
    public JAXBElement<GetExpandAvailHoldDate> createGetExpandAvailHoldDate(GetExpandAvailHoldDate value) {
        return new JAXBElement<GetExpandAvailHoldDate>(_GetExpandAvailHoldDate_QNAME, GetExpandAvailHoldDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendReceiveItemIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "sendReceiveItemIDResponse")
    public JAXBElement<SendReceiveItemIDResponse> createSendReceiveItemIDResponse(SendReceiveItemIDResponse value) {
        return new JAXBElement<SendReceiveItemIDResponse>(_SendReceiveItemIDResponse_QNAME, SendReceiveItemIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelExpiredHold }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "cancelExpiredHold")
    public JAXBElement<CancelExpiredHold> createCancelExpiredHold(CancelExpiredHold value) {
        return new JAXBElement<CancelExpiredHold>(_CancelExpiredHold_QNAME, CancelExpiredHold.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CatTitle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "catTitle")
    public JAXBElement<CatTitle> createCatTitle(CatTitle value) {
        return new JAXBElement<CatTitle>(_CatTitle_QNAME, CatTitle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckNewVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "checkNewVersionResponse")
    public JAXBElement<CheckNewVersionResponse> createCheckNewVersionResponse(CheckNewVersionResponse value) {
        return new JAXBElement<CheckNewVersionResponse>(_CheckNewVersionResponse_QNAME, CheckNewVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResultsWithHoldIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://holdnotice-service.tpl.gov.tw", name = "getResultsWithHoldIdResponse")
    public JAXBElement<GetResultsWithHoldIdResponse> createGetResultsWithHoldIdResponse(GetResultsWithHoldIdResponse value) {
        return new JAXBElement<GetResultsWithHoldIdResponse>(_GetResultsWithHoldIdResponse_QNAME, GetResultsWithHoldIdResponse.class, null, value);
    }

}
