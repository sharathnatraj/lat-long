
import java.util.List;

public class SupplierLocationMatchesList {

    private Integer providerId;
    private String providerSupplierCode;
    private Integer supplierId;
    private Double matchPercent;
    private Integer countProviderLocations;
    private Integer countLocCodeMatched;
    private Integer countDistanceMatched;
    private Integer countAddressMatched;
    private Integer countManualVerifyMatched;
    private Integer countInvalidProviderLocationList;
    private Integer countUnMatchedLocationList;
    private Integer countUnMatchedNoQCVLLocationList;
    private Integer countActiveMatchedCVL;
    private Integer countInactiveMatchedCVL;
    private Integer countActiveUnMatchedCVL;
    private Integer countInactiveUnMatchedCVL;
    private Integer countSupplierCVL;
    private Boolean supplierCVLCountCorrect;
    private Object locCodeMatchedLocationList;
    private Object distanceMatchedLocationList;
    private Object addressMatchedLocationList;
    private Object manualVerifyMatchedLocationList;
    private Object invalidProviderLocationList;
    private List<UnMatchedLocationList> unMatchedLocationList = null;
    private Object unMatchedNoQCVLLocationList;
    private Object inactiveMatchedCVLList;
    private Object activeMatchedCVLList;
    private Object inactiveUnMatchedCVLList;
    private Object activeUnMatchedCVLList;

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getProviderSupplierCode() {
        return providerSupplierCode;
    }

    public void setProviderSupplierCode(String providerSupplierCode) {
        this.providerSupplierCode = providerSupplierCode;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Double getMatchPercent() {
        return matchPercent;
    }

    public void setMatchPercent(Double matchPercent) {
        this.matchPercent = matchPercent;
    }

    public Integer getCountProviderLocations() {
        return countProviderLocations;
    }

    public void setCountProviderLocations(Integer countProviderLocations) {
        this.countProviderLocations = countProviderLocations;
    }

    public Integer getCountLocCodeMatched() {
        return countLocCodeMatched;
    }

    public void setCountLocCodeMatched(Integer countLocCodeMatched) {
        this.countLocCodeMatched = countLocCodeMatched;
    }

    public Integer getCountDistanceMatched() {
        return countDistanceMatched;
    }

    public void setCountDistanceMatched(Integer countDistanceMatched) {
        this.countDistanceMatched = countDistanceMatched;
    }

    public Integer getCountAddressMatched() {
        return countAddressMatched;
    }

    public void setCountAddressMatched(Integer countAddressMatched) {
        this.countAddressMatched = countAddressMatched;
    }

    public Integer getCountManualVerifyMatched() {
        return countManualVerifyMatched;
    }

    public void setCountManualVerifyMatched(Integer countManualVerifyMatched) {
        this.countManualVerifyMatched = countManualVerifyMatched;
    }

    public Integer getCountInvalidProviderLocationList() {
        return countInvalidProviderLocationList;
    }

    public void setCountInvalidProviderLocationList(Integer countInvalidProviderLocationList) {
        this.countInvalidProviderLocationList = countInvalidProviderLocationList;
    }

    public Integer getCountUnMatchedLocationList() {
        return countUnMatchedLocationList;
    }

    public void setCountUnMatchedLocationList(Integer countUnMatchedLocationList) {
        this.countUnMatchedLocationList = countUnMatchedLocationList;
    }

    public Integer getCountUnMatchedNoQCVLLocationList() {
        return countUnMatchedNoQCVLLocationList;
    }

    public void setCountUnMatchedNoQCVLLocationList(Integer countUnMatchedNoQCVLLocationList) {
        this.countUnMatchedNoQCVLLocationList = countUnMatchedNoQCVLLocationList;
    }

    public Integer getCountActiveMatchedCVL() {
        return countActiveMatchedCVL;
    }

    public void setCountActiveMatchedCVL(Integer countActiveMatchedCVL) {
        this.countActiveMatchedCVL = countActiveMatchedCVL;
    }

    public Integer getCountInactiveMatchedCVL() {
        return countInactiveMatchedCVL;
    }

    public void setCountInactiveMatchedCVL(Integer countInactiveMatchedCVL) {
        this.countInactiveMatchedCVL = countInactiveMatchedCVL;
    }

    public Integer getCountActiveUnMatchedCVL() {
        return countActiveUnMatchedCVL;
    }

    public void setCountActiveUnMatchedCVL(Integer countActiveUnMatchedCVL) {
        this.countActiveUnMatchedCVL = countActiveUnMatchedCVL;
    }

    public Integer getCountInactiveUnMatchedCVL() {
        return countInactiveUnMatchedCVL;
    }

    public void setCountInactiveUnMatchedCVL(Integer countInactiveUnMatchedCVL) {
        this.countInactiveUnMatchedCVL = countInactiveUnMatchedCVL;
    }

    public Integer getCountSupplierCVL() {
        return countSupplierCVL;
    }

    public void setCountSupplierCVL(Integer countSupplierCVL) {
        this.countSupplierCVL = countSupplierCVL;
    }

    public Boolean getSupplierCVLCountCorrect() {
        return supplierCVLCountCorrect;
    }

    public void setSupplierCVLCountCorrect(Boolean supplierCVLCountCorrect) {
        this.supplierCVLCountCorrect = supplierCVLCountCorrect;
    }

    public Object getLocCodeMatchedLocationList() {
        return locCodeMatchedLocationList;
    }

    public void setLocCodeMatchedLocationList(Object locCodeMatchedLocationList) {
        this.locCodeMatchedLocationList = locCodeMatchedLocationList;
    }

    public Object getDistanceMatchedLocationList() {
        return distanceMatchedLocationList;
    }

    public void setDistanceMatchedLocationList(Object distanceMatchedLocationList) {
        this.distanceMatchedLocationList = distanceMatchedLocationList;
    }

    public Object getAddressMatchedLocationList() {
        return addressMatchedLocationList;
    }

    public void setAddressMatchedLocationList(Object addressMatchedLocationList) {
        this.addressMatchedLocationList = addressMatchedLocationList;
    }

    public Object getManualVerifyMatchedLocationList() {
        return manualVerifyMatchedLocationList;
    }

    public void setManualVerifyMatchedLocationList(Object manualVerifyMatchedLocationList) {
        this.manualVerifyMatchedLocationList = manualVerifyMatchedLocationList;
    }

    public Object getInvalidProviderLocationList() {
        return invalidProviderLocationList;
    }

    public void setInvalidProviderLocationList(Object invalidProviderLocationList) {
        this.invalidProviderLocationList = invalidProviderLocationList;
    }

    public List<UnMatchedLocationList> getUnMatchedLocationList() {
        return unMatchedLocationList;
    }

    public void setUnMatchedLocationList(List<UnMatchedLocationList> unMatchedLocationList) {
        this.unMatchedLocationList = unMatchedLocationList;
    }

    public Object getUnMatchedNoQCVLLocationList() {
        return unMatchedNoQCVLLocationList;
    }

    public void setUnMatchedNoQCVLLocationList(Object unMatchedNoQCVLLocationList) {
        this.unMatchedNoQCVLLocationList = unMatchedNoQCVLLocationList;
    }

    public Object getInactiveMatchedCVLList() {
        return inactiveMatchedCVLList;
    }

    public void setInactiveMatchedCVLList(Object inactiveMatchedCVLList) {
        this.inactiveMatchedCVLList = inactiveMatchedCVLList;
    }

    public Object getActiveMatchedCVLList() {
        return activeMatchedCVLList;
    }

    public void setActiveMatchedCVLList(Object activeMatchedCVLList) {
        this.activeMatchedCVLList = activeMatchedCVLList;
    }

    public Object getInactiveUnMatchedCVLList() {
        return inactiveUnMatchedCVLList;
    }

    public void setInactiveUnMatchedCVLList(Object inactiveUnMatchedCVLList) {
        this.inactiveUnMatchedCVLList = inactiveUnMatchedCVLList;
    }

    public Object getActiveUnMatchedCVLList() {
        return activeUnMatchedCVLList;
    }

    public void setActiveUnMatchedCVLList(Object activeUnMatchedCVLList) {
        this.activeUnMatchedCVLList = activeUnMatchedCVLList;
    }

}
