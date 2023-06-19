package com.example.demo3.controller;

import com.example.demo3.model.*;
import com.example.demo3.persistence.ActionDB;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.control.TableView.UNCONSTRAINED_RESIZE_POLICY;
/**
 *  Data Controller
 */
public class DataController implements Initializable {
    @FXML
    private TextArea query;
    @FXML
    private Label choose;
    @FXML
    private AnchorPane anchorData;
    @FXML Button btnSearch;
    @FXML
    private ComboBox<String> comboBox;
    private Pagination pagination;
    ObservableList<String> entity = FXCollections.observableArrayList("Relic", "Figure", "Festival", "Event", "Dynasty");

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        comboBox.setItems(entity);
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }
/*
    Execute Action every time Combo Box is changed or search is pressed.
    You can search by name and category.
 */
    public void comboBoxChanged(ActionEvent event) {
        anchorData.getChildren().remove(pagination);
        if (!"btnSearch".equals(((Control)event.getSource()).getId())){
            query.setText("");
        }
        choose.setVisible(false);
        String keyword = query.getText();

        // show TableView with Pagination data followed by Combo Box 's Value, keyword
        showData(comboBox.getValue(), keyword);

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchorData.getChildren().addAll(pagination);
    }
    public void showData(String name, String keyword){
        if (name.equals("Relic")) {
            final int cnt = ActionDB.countRelic(keyword);
            if (cnt == 0) {
                choose.setText("Not data available for keyword: " + keyword);
                choose.setVisible(true);
                return;
            }
            pagination = new Pagination((cnt % rowsPerPage() == 0 ? cnt / rowsPerPage() : cnt / rowsPerPage() + 1), 0);
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= cnt) {
                    return null;
                } else {
                    return createRelicPage(pageIndex, keyword);
                }
            });
        } else if (name.equals("Figure")) {
            final int cnt = ActionDB.countFigure(keyword);
            if (cnt == 0) {
                choose.setText("Not data available for keyword: " + keyword);
                choose.setVisible(true);
                return;
            }
            pagination = new Pagination((cnt % rowsPerPage() == 0 ? cnt / rowsPerPage() : cnt / rowsPerPage() + 1), 0);
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= cnt) {
                    return null;
                } else {
                    return createFigurePage(pageIndex, keyword);
                }
            });
        }else if (name.equals("Event")) {
            final int cnt = ActionDB.countEvent(keyword);
            if (cnt == 0) {
                choose.setText("Not data available for keyword: " + keyword);
                choose.setVisible(true);
                return;
            }
            pagination = new Pagination((cnt % rowsPerPage() == 0 ? cnt / rowsPerPage() : cnt / rowsPerPage() + 1), 0);
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= cnt) {
                    return null;
                } else {
                    return createEventPage(pageIndex, keyword);
                }
            });
        }else if (name.equals("Dynasty")) {
            final int cnt = ActionDB.countDynasty(keyword);
            if (cnt == 0) {
                choose.setText("Not data available for keyword: " + keyword);
                choose.setVisible(true);
                return;
            }
            pagination = new Pagination((cnt % rowsPerPage() == 0 ? cnt / rowsPerPage() : cnt / rowsPerPage() + 1), 0);
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= cnt) {
                    return null;
                } else {
                    return createDynastyPage(pageIndex, keyword);
                }
            });
        }else if (name.equals("Festival")) {
            final int cnt = ActionDB.countFestival(keyword);
            if (cnt == 0) {
                choose.setText("Not data available for keyword: " + keyword);
                choose.setVisible(true);
                return;
            }
            pagination = new Pagination((cnt % rowsPerPage() == 0 ? cnt / rowsPerPage() : cnt / rowsPerPage() + 1), 0);
            pagination.setPageFactory((Integer pageIndex) -> {
                if (pageIndex >= cnt) {
                    return null;
                } else {
                    return createFestivalPage(pageIndex, keyword);
                }
            });
        }
    }

    public VBox createRelicPage(int pageIndex, String query) {
        ObservableList<Relic> data = FXCollections.observableArrayList(ActionDB.getListRelic(query));
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Relic> table = new TableView<Relic>();
            table.setMinHeight(290);
            table.setMinWidth(750);
            TableColumn nameCol = new TableColumn("name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("name"));
            TableColumn addressCol = new TableColumn("address");
            addressCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("address"));
            TableColumn foundCol = new TableColumn("found");
            foundCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("found"));
            TableColumn founderCol = new TableColumn("founder");
            founderCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("founder"));
            TableColumn embellishedCol = new TableColumn("embellished");
            embellishedCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("embellished"));
            TableColumn newConstructionCol = new TableColumn("newConstruction");
            newConstructionCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("newConstruction"));
            TableColumn festivalTimeCol = new TableColumn("festivalTime");
            festivalTimeCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("festivalTime"));
            TableColumn urlCol = new TableColumn("url");
            urlCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("url"));

            table.getColumns().addAll(nameCol, urlCol, addressCol, embellishedCol, festivalTimeCol, foundCol, founderCol, newConstructionCol);

            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(table);
        }
        return box;
    }

    public VBox createFigurePage(int pageIndex, String query) {
        ObservableList<Figure> data = FXCollections.observableArrayList(ActionDB.getListFigure(query));
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Figure> table = new TableView<Figure>();
            table.setMinHeight(290);
            table.setMinWidth(750);
            TableColumn nameCol = new TableColumn("name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("name"));
            TableColumn ruleTimeCol = new TableColumn("ruleTime");
            ruleTimeCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("ruleTime"));
            TableColumn lifeTimeCol = new TableColumn("lifeTime");
            lifeTimeCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("lifeTime"));
            TableColumn burialCol = new TableColumn("burial");
            burialCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("burial"));
            TableColumn honorNameCol = new TableColumn("honorName");
            honorNameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("honorName"));
            TableColumn swissBrandCol = new TableColumn("swissBrand");
            swissBrandCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("swissBrand"));
            TableColumn templeBrandCol = new TableColumn("templeBrand");
            templeBrandCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("templeBrand"));
            TableColumn dynastyCol = new TableColumn("dynasty");
            dynastyCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("dynasty"));
            TableColumn fatherCol = new TableColumn("father");
            fatherCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("father"));
            TableColumn motherCol = new TableColumn("mother");
            motherCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("mother"));
            TableColumn religionCol = new TableColumn("religion");
            religionCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("religion"));
            TableColumn relicCol = new TableColumn("relic");
            relicCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("relic"));
            TableColumn urlCol = new TableColumn("url");
            urlCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("url"));
            TableColumn concubineCol = new TableColumn("concubine");
            concubineCol.setCellValueFactory(new PropertyValueFactory<Relic, List<String>>("concubine"));
            TableColumn realNameCol = new TableColumn("realName");
            realNameCol.setCellValueFactory(new PropertyValueFactory<Relic, List<String>>("realName"));
            TableColumn labelCol = new TableColumn("label");
            labelCol.setCellValueFactory(new PropertyValueFactory<Relic, List<String>>("label"));

            table.getColumns().addAll(nameCol,
                    urlCol,
                    ruleTimeCol,
                    lifeTimeCol,
                    burialCol,
                    honorNameCol,
                    swissBrandCol,
                    templeBrandCol,
                    dynastyCol,
                    fatherCol,
                    motherCol,
                    religionCol,
                    relicCol,
                    concubineCol,
                    realNameCol,
                    labelCol);

            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(table);
        }
        return box;
    }

    public VBox createEventPage(int pageIndex, String query) {
        ObservableList<Event> data = FXCollections.observableArrayList(ActionDB.getListEvent(query));
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Event> table = new TableView<Event>();
            table.setMinHeight(290);
            table.setMinWidth(750);
            TableColumn nameCol = new TableColumn("name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("name"));
            TableColumn timeCol = new TableColumn("time");
            timeCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("time"));
            TableColumn addressCol = new TableColumn("address");
            addressCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("address"));
            TableColumn resultCol = new TableColumn("result");
            resultCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("result"));
            TableColumn skateholdersCol = new TableColumn("skateholders");
            skateholdersCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("skateholders"));
            TableColumn urlCol = new TableColumn("url");
            urlCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("url"));

            table.getColumns().addAll(nameCol,
                    urlCol,
                    timeCol,
                    addressCol,
                    resultCol,
                    skateholdersCol);

            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(table);
        }
        return box;
    }

    public VBox createDynastyPage(int pageIndex, String query) {
        ObservableList<Dynasty> data = FXCollections.observableArrayList(ActionDB.getListDynasty(query));
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Dynasty> table = new TableView<Dynasty>();
            table.setMinHeight(290);
            table.setMinWidth(750);
            //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn nameCol = new TableColumn("name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("name"));
            TableColumn countryCol = new TableColumn("country");
            countryCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("country"));
            TableColumn ruleTimeCol = new TableColumn("ruleTime");
            ruleTimeCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("ruleTime"));
            TableColumn popularLanguageCol = new TableColumn("popularLanguage");
            popularLanguageCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("popularLanguage"));
            TableColumn mainReligionCol = new TableColumn("mainReligion");
            mainReligionCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("mainReligion"));
            TableColumn governmentCol = new TableColumn("government");
            governmentCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("government"));
            TableColumn currencyUnitCol = new TableColumn("currencyUnit");
            currencyUnitCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("currencyUnit"));
            TableColumn urlCol = new TableColumn("url");
            urlCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("url"));
            TableColumn capitalCol = new TableColumn("capital");
            capitalCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("capital"));
            TableColumn kingCol = new TableColumn("king");
            kingCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("king"));
            TableColumn eventCol = new TableColumn("event");
            eventCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("event"));

            table.getColumns().addAll(nameCol,
                    urlCol,
                    countryCol,
                    ruleTimeCol,
                    popularLanguageCol,
                    mainReligionCol,
                    governmentCol,
                    currencyUnitCol,
                    capitalCol,
                    kingCol,
                    eventCol);

            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(table);
        }
        return box;
    }

    public VBox createFestivalPage(int pageIndex, String query) {
        ObservableList<Festival> data = FXCollections.observableArrayList(ActionDB.getListFestival(query));
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage();
        } else {
            lastIndex = data.size() / rowsPerPage() - 1;

        }
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TableView<Festival> table = new TableView<Festival>();
            table.setMinHeight(290);
            table.setMinWidth(750);
            TableColumn nameCol = new TableColumn("name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("name"));
            TableColumn locationCol = new TableColumn("location");
            locationCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("location"));
            TableColumn anniversaryCol = new TableColumn("anniversary");
            anniversaryCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("anniversary"));
            TableColumn significanceCol = new TableColumn("significance");
            significanceCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("significance"));
            TableColumn figureCol = new TableColumn("figure");
            figureCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("figure"));
            TableColumn eventCol = new TableColumn("event");
            eventCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("event"));
            TableColumn relicCol = new TableColumn("relic");
            relicCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("relic"));
            TableColumn urlCol = new TableColumn("url");
            urlCol.setCellValueFactory(new PropertyValueFactory<Relic, String>("url"));

            table.getColumns().addAll(nameCol,
                    urlCol,
                    locationCol,
                    anniversaryCol,
                    significanceCol,
                    figureCol,
                    eventCol,
                    relicCol);

            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + rowsPerPage())));
            }

            box.getChildren().add(table);
        }
        return box;
    }
}