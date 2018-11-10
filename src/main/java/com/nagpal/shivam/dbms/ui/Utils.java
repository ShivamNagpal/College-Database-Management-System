package com.nagpal.shivam.dbms.ui;

import com.nagpal.shivam.dbms.model.DepartmentData;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

class Utils {
    static Callback<ListView<DepartmentData>, ListCell<DepartmentData>> departmentChoiceBoxCallback = new Callback<ListView<DepartmentData>, ListCell<DepartmentData>>() {
        @Override
        public ListCell<DepartmentData> call(ListView<DepartmentData> param) {

            return new ListCell<DepartmentData>() {
                @Override
                protected void updateItem(DepartmentData item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.departmentId + " | " + item.name);
                    }
                }
            };
        }
    };

}
