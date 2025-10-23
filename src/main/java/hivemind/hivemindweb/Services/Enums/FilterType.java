package hivemind.hivemindweb.Services.Enums;

public enum FilterType {
    INPUT_TEXT, INPUT_OPTION, INPUT_CPF, INPUT_SECTOR, ALL_VALUES;

    public enum Payment{
        ALL_VALUES, ID_PLAN_SUBSCRIPTION, PENDING, PAID, CANCELED
    }

    public enum PlanSubscription {
        ALL_VALUES, ID_PLAN, CNPJ_COMPANY
    }
}
