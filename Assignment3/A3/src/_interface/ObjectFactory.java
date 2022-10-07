
package _interface;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the _interface package. 
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

    private final static QName _SwapAppointment_QNAME = new QName("http://Interface/", "swapAppointment");
    private final static QName _ListAppointmentAvailabilityResponse_QNAME = new QName("http://Interface/", "listAppointmentAvailabilityResponse");
    private final static QName _SwapAppointmentResponse_QNAME = new QName("http://Interface/", "swapAppointmentResponse");
    private final static QName _BookAppointment_QNAME = new QName("http://Interface/", "bookAppointment");
    private final static QName _AddAppointmentResponse_QNAME = new QName("http://Interface/", "addAppointmentResponse");
    private final static QName _CancelAppointment_QNAME = new QName("http://Interface/", "cancelAppointment");
    private final static QName _AddAppointment_QNAME = new QName("http://Interface/", "addAppointment");
    private final static QName _GetAppointmentScheduleResponse_QNAME = new QName("http://Interface/", "getAppointmentScheduleResponse");
    private final static QName _RemoveAppointment_QNAME = new QName("http://Interface/", "removeAppointment");
    private final static QName _RemoveAppointmentResponse_QNAME = new QName("http://Interface/", "removeAppointmentResponse");
    private final static QName _GetAppointmentSchedule_QNAME = new QName("http://Interface/", "getAppointmentSchedule");
    private final static QName _CancelAppointmentResponse_QNAME = new QName("http://Interface/", "cancelAppointmentResponse");
    private final static QName _BookAppointmentResponse_QNAME = new QName("http://Interface/", "bookAppointmentResponse");
    private final static QName _ListAppointmentAvailability_QNAME = new QName("http://Interface/", "listAppointmentAvailability");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: _interface
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelAppointment }
     * 
     */
    public CancelAppointment createCancelAppointment() {
        return new CancelAppointment();
    }

    /**
     * Create an instance of {@link AddAppointment }
     * 
     */
    public AddAppointment createAddAppointment() {
        return new AddAppointment();
    }

    /**
     * Create an instance of {@link ListAppointmentAvailabilityResponse }
     * 
     */
    public ListAppointmentAvailabilityResponse createListAppointmentAvailabilityResponse() {
        return new ListAppointmentAvailabilityResponse();
    }

    /**
     * Create an instance of {@link SwapAppointmentResponse }
     * 
     */
    public SwapAppointmentResponse createSwapAppointmentResponse() {
        return new SwapAppointmentResponse();
    }

    /**
     * Create an instance of {@link SwapAppointment }
     * 
     */
    public SwapAppointment createSwapAppointment() {
        return new SwapAppointment();
    }

    /**
     * Create an instance of {@link AddAppointmentResponse }
     * 
     */
    public AddAppointmentResponse createAddAppointmentResponse() {
        return new AddAppointmentResponse();
    }

    /**
     * Create an instance of {@link BookAppointment }
     * 
     */
    public BookAppointment createBookAppointment() {
        return new BookAppointment();
    }

    /**
     * Create an instance of {@link BookAppointmentResponse }
     * 
     */
    public BookAppointmentResponse createBookAppointmentResponse() {
        return new BookAppointmentResponse();
    }

    /**
     * Create an instance of {@link ListAppointmentAvailability }
     * 
     */
    public ListAppointmentAvailability createListAppointmentAvailability() {
        return new ListAppointmentAvailability();
    }

    /**
     * Create an instance of {@link RemoveAppointment }
     * 
     */
    public RemoveAppointment createRemoveAppointment() {
        return new RemoveAppointment();
    }

    /**
     * Create an instance of {@link RemoveAppointmentResponse }
     * 
     */
    public RemoveAppointmentResponse createRemoveAppointmentResponse() {
        return new RemoveAppointmentResponse();
    }

    /**
     * Create an instance of {@link GetAppointmentScheduleResponse }
     * 
     */
    public GetAppointmentScheduleResponse createGetAppointmentScheduleResponse() {
        return new GetAppointmentScheduleResponse();
    }

    /**
     * Create an instance of {@link GetAppointmentSchedule }
     * 
     */
    public GetAppointmentSchedule createGetAppointmentSchedule() {
        return new GetAppointmentSchedule();
    }

    /**
     * Create an instance of {@link CancelAppointmentResponse }
     * 
     */
    public CancelAppointmentResponse createCancelAppointmentResponse() {
        return new CancelAppointmentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SwapAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "swapAppointment")
    public JAXBElement<SwapAppointment> createSwapAppointment(SwapAppointment value) {
        return new JAXBElement<SwapAppointment>(_SwapAppointment_QNAME, SwapAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAppointmentAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "listAppointmentAvailabilityResponse")
    public JAXBElement<ListAppointmentAvailabilityResponse> createListAppointmentAvailabilityResponse(ListAppointmentAvailabilityResponse value) {
        return new JAXBElement<ListAppointmentAvailabilityResponse>(_ListAppointmentAvailabilityResponse_QNAME, ListAppointmentAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SwapAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "swapAppointmentResponse")
    public JAXBElement<SwapAppointmentResponse> createSwapAppointmentResponse(SwapAppointmentResponse value) {
        return new JAXBElement<SwapAppointmentResponse>(_SwapAppointmentResponse_QNAME, SwapAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "bookAppointment")
    public JAXBElement<BookAppointment> createBookAppointment(BookAppointment value) {
        return new JAXBElement<BookAppointment>(_BookAppointment_QNAME, BookAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "addAppointmentResponse")
    public JAXBElement<AddAppointmentResponse> createAddAppointmentResponse(AddAppointmentResponse value) {
        return new JAXBElement<AddAppointmentResponse>(_AddAppointmentResponse_QNAME, AddAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "cancelAppointment")
    public JAXBElement<CancelAppointment> createCancelAppointment(CancelAppointment value) {
        return new JAXBElement<CancelAppointment>(_CancelAppointment_QNAME, CancelAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "addAppointment")
    public JAXBElement<AddAppointment> createAddAppointment(AddAppointment value) {
        return new JAXBElement<AddAppointment>(_AddAppointment_QNAME, AddAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppointmentScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "getAppointmentScheduleResponse")
    public JAXBElement<GetAppointmentScheduleResponse> createGetAppointmentScheduleResponse(GetAppointmentScheduleResponse value) {
        return new JAXBElement<GetAppointmentScheduleResponse>(_GetAppointmentScheduleResponse_QNAME, GetAppointmentScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "removeAppointment")
    public JAXBElement<RemoveAppointment> createRemoveAppointment(RemoveAppointment value) {
        return new JAXBElement<RemoveAppointment>(_RemoveAppointment_QNAME, RemoveAppointment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "removeAppointmentResponse")
    public JAXBElement<RemoveAppointmentResponse> createRemoveAppointmentResponse(RemoveAppointmentResponse value) {
        return new JAXBElement<RemoveAppointmentResponse>(_RemoveAppointmentResponse_QNAME, RemoveAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAppointmentSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "getAppointmentSchedule")
    public JAXBElement<GetAppointmentSchedule> createGetAppointmentSchedule(GetAppointmentSchedule value) {
        return new JAXBElement<GetAppointmentSchedule>(_GetAppointmentSchedule_QNAME, GetAppointmentSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "cancelAppointmentResponse")
    public JAXBElement<CancelAppointmentResponse> createCancelAppointmentResponse(CancelAppointmentResponse value) {
        return new JAXBElement<CancelAppointmentResponse>(_CancelAppointmentResponse_QNAME, CancelAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAppointmentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "bookAppointmentResponse")
    public JAXBElement<BookAppointmentResponse> createBookAppointmentResponse(BookAppointmentResponse value) {
        return new JAXBElement<BookAppointmentResponse>(_BookAppointmentResponse_QNAME, BookAppointmentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAppointmentAvailability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Interface/", name = "listAppointmentAvailability")
    public JAXBElement<ListAppointmentAvailability> createListAppointmentAvailability(ListAppointmentAvailability value) {
        return new JAXBElement<ListAppointmentAvailability>(_ListAppointmentAvailability_QNAME, ListAppointmentAvailability.class, null, value);
    }

}
