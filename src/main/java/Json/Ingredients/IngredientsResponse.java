package Json.Ingredients;

import java.util.List;

public class IngredientsResponse {
    private String success;
    private List<IngredientsData> data;

    public IngredientsResponse(String success, List<IngredientsData> dataList) {
        this.success = success;
        this.data = dataList;
    }

    public IngredientsResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<IngredientsData> getDataList() {
        return data;
    }

    public void setDataList(List<IngredientsData> dataList) {
        this.data = dataList;
    }
}
