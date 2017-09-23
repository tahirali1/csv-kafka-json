package models;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public final class FlInsuranceData {

    private final int policyId;
    private final String stateCode;
    private final String country;
    private final String construction;

    private FlInsuranceData(final Builder builder) {
        this.policyId = builder.policyId;
        this.stateCode = builder.stateCode;
        this.country = builder.country;
        this.construction = builder.construction;
    }

    public int getPolicyId() { return this.policyId; }
    public String getStateCode() { return this.stateCode; }
    public String getCountry() { return this.country; }
    public String getConstruction() { return this.construction; }

    @Override
    public int hashCode() {
        return Objects.hash(policyId, stateCode, country, construction);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FlInsuranceData other = (FlInsuranceData) obj;
        return Objects.equals(this.policyId, other.policyId)
                && Objects.equals(this.stateCode, other.stateCode)
                && Objects.equals(this.country, other.country)
                && Objects.equals(this.construction, other.construction);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("policyId", policyId)
                .add("stateCode", stateCode)
                .add("country", country)
                .add("construction", construction)
                .toString();
    }

    public static class Builder{
        private int policyId;
        private String stateCode;
        private String country;
        private String construction;

        public Builder policyId(final int policyId) {
            this.policyId = policyId;
            return this;
        }

        public Builder stateCode(final String stateCode) {
            this.stateCode = stateCode;
            return this;
        }

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Builder construction(final String construction) {
            this.construction = construction;
            return this;
        }

        public FlInsuranceData build() {
            return new FlInsuranceData(this);
        }
    }

}
