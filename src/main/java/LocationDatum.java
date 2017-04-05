
import java.util.List;

 class LocationData {

    private ProviderData providerData;
    private List<CvlLocDataList> cvlLocDataList = null;
    private CreatedVendorLocation createdVendorLocation;

    public ProviderData getProviderData() {
        return providerData;
    }

    public void setProviderData(ProviderData providerData) {
        this.providerData = providerData;
    }

    public List<CvlLocDataList> getCvlLocDataList() {
        return cvlLocDataList;
    }

    public void setCvlLocDataList(List<CvlLocDataList> cvlLocDataList) {
        this.cvlLocDataList = cvlLocDataList;
    }

    public CreatedVendorLocation getCreatedVendorLocation() {
        return createdVendorLocation;
    }

    public void setCreatedVendorLocation(CreatedVendorLocation createdVendorLocation) {
        this.createdVendorLocation = createdVendorLocation;
    }

}
