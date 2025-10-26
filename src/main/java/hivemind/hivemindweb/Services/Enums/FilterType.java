package hivemind.hivemindweb.Services.Enums;

public enum FilterType {;
    public enum Company{
        ACTIVE, INACTIVE, WITH_PENDING_PAYMENT, ALL_VALUES;
    }

    public enum Payment{
        ALL_VALUES, ID_PLAN_SUBSCRIPTION, PENDING, PAID, CANCELED
    }

    public enum PlanSubscription {
        ALL_VALUES, ID_PLAN, CNPJ_COMPANY
    }

    public enum Plant{
        COMPANY_NAME, ALL_VALUES, ACTIVE, INACTIVE
    }

    public enum Worker {
        ALL_VALUES, CPF, SECTOR
    }
}
