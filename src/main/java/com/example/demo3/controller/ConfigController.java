package com.example.demo3.controller;


import com.example.demo3.model.*;
import com.example.demo3.service.scrap.Scrap;
import com.example.demo3.service.crawler.*;
import com.example.demo3.service.crawler.RelicCrawler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.demo3.persistence.ActionDB.*;


/**
 *     Config Controller.
 *     Outstanding function: Input necessary field to get data.
 *                         You can preview and save to databases.
 *                         We always check duplicate data before insert. If there is a duplicate, we will update.
 */
public class ConfigController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label choose;
    @FXML
    private AnchorPane anchorConfig;
    private GridPane grid;
    private Pagination pagination;
    @FXML
    private AnchorPane anchorDataConfig;

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    ObservableList<String> entity = FXCollections.observableArrayList("Relic", "Figure", "Festival", "Event", "Dynasty");

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        comboBox.setItems(entity);
    }

    public void comboBoxChanged(ActionEvent event) {
        anchorDataConfig.getChildren().clear();
        choose.setVisible(false);
        anchorConfig.getChildren().clear();
        grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        if ("Relic".equals(comboBox.getValue())) {
            showRelicConfig();
        } else if ("Figure".equals(comboBox.getValue())) {
            showFigureConfig();
        } else if ("Festival".equals(comboBox.getValue())) {
            showFestivalConfig();
        } else if ("Event".equals(comboBox.getValue())) {
            showEventConfig();
        } else if ("Dynasty".equals(comboBox.getValue())) {
            showDynastyConfig();
        }

        AnchorPane.setTopAnchor(grid, 10.0);
        AnchorPane.setRightAnchor(grid, 10.0);
        AnchorPane.setBottomAnchor(grid, 10.0);
        AnchorPane.setLeftAnchor(grid, 10.0);
        anchorConfig.getChildren().addAll(grid);
    }

    private void showRelicConfig() {
        TextField url = new TextField();
        url.setPromptText("url");
        url.setPrefColumnCount(5);
        GridPane.setConstraints(url, 0, 0);
        grid.getChildren().add(url);

        GridPane cnt = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField parentTag = new TextField();
        parentTag.setPromptText("Parent Tag");
        GridPane.setConstraints(parentTag, 0, 0);
        cnt.getChildren().add(parentTag);
        TextField start = new TextField();

        start.setPromptText("start");
        start.setMaxWidth(40);
        GridPane.setConstraints(start, 1, 0);
        cnt.getChildren().add(start);

        TextField end = new TextField();
        end.setPromptText("end");
        end.setMaxWidth(40);
        GridPane.setConstraints(end, 2, 0);
        cnt.getChildren().add(end);

        TextField step = new TextField();
        step.setPromptText("step");
        step.setMaxWidth(40);
        GridPane.setConstraints(step, 3, 0);
        cnt.getChildren().add(step);

        GridPane.setConstraints(cnt, 0, 1);
        grid.getChildren().add(cnt);

        TextField name = new TextField();
        name.setPromptText("name");
        GridPane.setConstraints(name, 0, 2);
        grid.getChildren().add(name);

        TextField address = new TextField();
        address.setPromptText("address");
        GridPane.setConstraints(address, 0, 3);
        grid.getChildren().add(address);

        GridPane fd = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField found = new TextField();
        found.setPromptText("found");
        GridPane.setConstraints(found, 0, 0);
        fd.getChildren().add(found);

        TextField founder = new TextField();
        founder.setPromptText("founder");
        GridPane.setConstraints(founder, 1, 0);
        fd.getChildren().add(founder);

        GridPane.setConstraints(fd, 0, 4);
        grid.getChildren().add(fd);

        TextField embellished = new TextField();
        embellished.setPromptText("embellished");
        GridPane.setConstraints(embellished, 0, 5);
        grid.getChildren().add(embellished);

        TextField newConstruction = new TextField();
        newConstruction.setPromptText("New Construction");
        GridPane.setConstraints(newConstruction, 0, 6);
        grid.getChildren().add(newConstruction);

        TextField festivalTime = new TextField();
        festivalTime.setPromptText("Festival Time");
        GridPane.setConstraints(festivalTime, 0, 7);
        grid.getChildren().add(festivalTime);

        GridPane btn = new GridPane();

        Button preview = new Button("Preview");
        preview.maxWidth(40);
        GridPane.setConstraints(preview, 0, 0);
        btn.getChildren().add(preview);
        Button clear = new Button("Clear");
        clear.maxWidth(40);
        GridPane.setConstraints(clear, 1, 0);
        btn.getChildren().add(clear);
        Button save = new Button("Save");
        save.maxWidth(40);
        save.setVisible(false);
        GridPane.setConstraints(save, 3, 0);
        btn.getChildren().add(save);

        GridPane.setConstraints(btn, 0, 8);
        grid.getChildren().add(btn);
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                anchorDataConfig.getChildren().clear();
                List<String> urls = new ArrayList<>();
                urls = checkTypeUrl(url.getText(), start.getText(), end.getText(), step.getText(), parentTag.getText());
                if (urls == null) {
                    return;
                }
                save.setVisible(true);
                HashMap<String, String> config = new HashMap<>();
                config.put("name", name.getText());
                config.put("address", address.getText());
                config.put("found", found.getText());
                config.put("founder", founder.getText());
                config.put("embellished", embellished.getText());
                config.put("newConstruction", newConstruction.getText());
                config.put("festivalTime", festivalTime.getText());

                RelicCrawler crawler = new RelicCrawler();
                try {
                    List<Relic> data = crawler.crawlRelic(urls, config);

                    if (data.size() == 0) {
                        Label label = new Label();
                        label.setText("No data available");
                        label.setVisible(true);
                        anchorDataConfig.getChildren().add(label);
                    } else {
                        save.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    saveRelic(data);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                        int numberPage = data.size() % rowsPerPage() == 0 ? data.size() / rowsPerPage() : data.size() / rowsPerPage() + 1;
                        pagination = new Pagination(numberPage);
                        pagination.setPageFactory((Integer pageIndex) -> {
                            if (pageIndex > numberPage) {
                                return null;
                            } else {
                                return createRelicPage(pageIndex, FXCollections.observableArrayList(data));
                            }
                        });

                        AnchorPane.setTopAnchor(pagination, 10.0);
                        AnchorPane.setRightAnchor(pagination, 10.0);
                        AnchorPane.setBottomAnchor(pagination, 10.0);
                        AnchorPane.setLeftAnchor(pagination, 10.0);
                        anchorDataConfig.getChildren().addAll(pagination);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.setText("");
                address.setText("");
                found.setText("");
                founder.setText("");
                embellished.setText("");
                newConstruction.setText("");
                festivalTime.setText("");
                url.setText("");
                parentTag.setText("");
                start.setText("");
                step.setText("");
                end.setText("");
            }
        });
    }

    private void showFigureConfig() {
        TextField url = new TextField();
        url.setPromptText("url");
        url.setPrefColumnCount(5);
        GridPane.setConstraints(url, 0, 0);
        grid.getChildren().add(url);

        GridPane cnt = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField parentTag = new TextField();
        parentTag.setPromptText("Parent Tag");
        GridPane.setConstraints(parentTag, 0, 0);
        cnt.getChildren().add(parentTag);
        TextField start = new TextField();

        start.setPromptText("start");
        start.setMaxWidth(40);
        GridPane.setConstraints(start, 1, 0);
        cnt.getChildren().add(start);

        TextField end = new TextField();
        end.setPromptText("end");
        end.setMaxWidth(40);
        GridPane.setConstraints(end, 2, 0);
        cnt.getChildren().add(end);

        TextField step = new TextField();
        step.setPromptText("step");
        step.setMaxWidth(40);
        GridPane.setConstraints(step, 3, 0);
        cnt.getChildren().add(step);

        GridPane.setConstraints(cnt, 0, 1);
        grid.getChildren().add(cnt);

        TextField name = new TextField();
        name.setPromptText("name");
        GridPane.setConstraints(name, 0, 2);
        grid.getChildren().add(name);

        GridPane time = new GridPane();
        TextField ruleTime = new TextField();
        ruleTime.setPromptText("ruleTime");
        GridPane.setConstraints(ruleTime, 0, 0);
        time.getChildren().add(ruleTime);

        TextField lifeTime = new TextField();
        lifeTime.setPromptText("lifeTime");
        GridPane.setConstraints(lifeTime, 1, 0);
        time.getChildren().add(lifeTime);

        GridPane.setConstraints(time, 0, 3);
        grid.getChildren().add(time);

        TextField burial = new TextField();
        burial.setPromptText("burial");
        GridPane.setConstraints(burial, 0, 4);
        grid.getChildren().add(burial);

        TextField honorName = new TextField();
        honorName.setPromptText("honorName");
        GridPane.setConstraints(honorName, 0, 5);
        grid.getChildren().add(honorName);

        TextField swissBrand = new TextField();
        swissBrand.setPromptText("swissBrand");
        GridPane.setConstraints(swissBrand, 0, 6);
        grid.getChildren().add(swissBrand);

        TextField templeBrand = new TextField();
        templeBrand.setPromptText("templeBrand");
        GridPane.setConstraints(templeBrand, 0, 7);
        grid.getChildren().add(templeBrand);

        TextField dynasty = new TextField();
        dynasty.setPromptText("dynasty");
        GridPane.setConstraints(dynasty, 0, 8);
        grid.getChildren().add(dynasty);

        GridPane parents = new GridPane();
        TextField father = new TextField();
        father.setPromptText("father");
        GridPane.setConstraints(father, 0, 0);
        parents.getChildren().add(father);

        TextField mother = new TextField();
        mother.setPromptText("mother");
        GridPane.setConstraints(mother, 1, 0);
        parents.getChildren().add(mother);
        GridPane.setConstraints(parents, 0, 9);
        grid.getChildren().add(parents);

        GridPane re = new GridPane();
        TextField religion = new TextField();
        religion.setPromptText("religion");
        GridPane.setConstraints(religion, 0, 0);
        re.getChildren().add(religion);

        TextField relic = new TextField();
        relic.setPromptText("relic");
        GridPane.setConstraints(relic, 1, 0);
        re.getChildren().add(relic);
        GridPane.setConstraints(re, 0, 10);
        grid.getChildren().add(re);

        TextField concubine = new TextField();
        concubine.setPromptText("concubine");
        GridPane.setConstraints(concubine, 0, 11);
        grid.getChildren().add(concubine);

        TextField realName = new TextField();
        realName.setPromptText("realName");
        GridPane.setConstraints(realName, 0, 12);
        grid.getChildren().add(realName);

        TextField label = new TextField();
        label.setPromptText("label");
        GridPane.setConstraints(label, 0, 13);
        grid.getChildren().add(label);

        GridPane btn = new GridPane();

        Button preview = new Button("Preview");
        preview.maxWidth(40);
        GridPane.setConstraints(preview, 0, 0);
        btn.getChildren().add(preview);
        Button clear = new Button("Clear");
        clear.maxWidth(40);
        GridPane.setConstraints(clear, 1, 0);
        btn.getChildren().add(clear);
        Button save = new Button("Save");
        save.maxWidth(40);
        save.setVisible(false);
        GridPane.setConstraints(save, 3, 0);
        btn.getChildren().add(save);

        GridPane.setConstraints(btn, 0, 14);
        grid.getChildren().add(btn);
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                anchorDataConfig.getChildren().clear();
                List<String> urls = new ArrayList<>();
                urls = checkTypeUrl(url.getText(), start.getText(), end.getText(), step.getText(), parentTag.getText());
                if (urls == null) {
                    return;
                }
                save.setVisible(true);
                HashMap<String, String> config = new HashMap<>();
                HashMap<String, List<String>> configList = new HashMap<>();
                config.put("name", name.getText());
                config.put("ruleTime", ruleTime.getText());
                config.put("lifeTime", lifeTime.getText());
                config.put("burial", burial.getText());
                config.put("honorName", honorName.getText());
                config.put("swissBrand", swissBrand.getText());
                config.put("templeBrand", templeBrand.getText());
                config.put("dynasty", dynasty.getText());
                config.put("father", father.getText());
                config.put("mother", mother.getText());
                config.put("religion", religion.getText());
                config.put("relic", relic.getText());

                configList.put("concubine", Arrays.asList(concubine.getText().split(",")));
                configList.put("realName", Arrays.asList(realName.getText().split(",")));
                configList.put("label", Arrays.asList(label.getText().split(",")));

                FigureCrawler crawler = new FigureCrawler();
                try {
                    List<Figure> data = crawler.crawlFigure(urls, config, configList);
                    if (data.size() == 0) {
                        Label label = new Label();
                        label.setText("No data available");
                        label.setVisible(true);
                        anchorDataConfig.getChildren().add(label);
                    } else {
                        save.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    saveFigure(data);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                        int numberPage = data.size() % rowsPerPage() == 0 ? data.size() / rowsPerPage() : data.size() / rowsPerPage() + 1;
                        pagination = new Pagination(numberPage, 0);
                        pagination.setPageFactory((Integer pageIndex) -> {
                            if (pageIndex > numberPage) {
                                return null;
                            } else {
                                return createFigurePage(pageIndex, FXCollections.observableArrayList(data));
                            }
                        });

                        AnchorPane.setTopAnchor(pagination, 10.0);
                        AnchorPane.setRightAnchor(pagination, 10.0);
                        AnchorPane.setBottomAnchor(pagination, 10.0);
                        AnchorPane.setLeftAnchor(pagination, 10.0);
                        anchorDataConfig.getChildren().addAll(pagination);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.setText("");
                url.setText("");
                parentTag.setText("");
                ruleTime.setText("");
                lifeTime.setText("");
                burial.setText("");
                honorName.setText("");
                swissBrand.setText("");
                templeBrand.setText("");
                dynasty.setText("");
                father.setText("");
                mother.setText("");
                religion.setText("");
                relic.setText("");
                start.setText("");
                step.setText("");
                end.setText("");
            }
        });
    }

    private void showFestivalConfig() {
        TextField url = new TextField();
        url.setPromptText("url");
        url.setPrefColumnCount(5);
        GridPane.setConstraints(url, 0, 0);
        grid.getChildren().add(url);

        GridPane cnt = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField parentTag = new TextField();
        parentTag.setPromptText("Parent Tag");
        GridPane.setConstraints(parentTag, 0, 0);
        cnt.getChildren().add(parentTag);
        TextField start = new TextField();

        start.setPromptText("start");
        start.setMaxWidth(40);
        GridPane.setConstraints(start, 1, 0);
        cnt.getChildren().add(start);

        TextField end = new TextField();
        end.setPromptText("end");
        end.setMaxWidth(40);
        GridPane.setConstraints(end, 2, 0);
        cnt.getChildren().add(end);

        TextField step = new TextField();
        step.setPromptText("step");
        step.setMaxWidth(40);
        GridPane.setConstraints(step, 3, 0);
        cnt.getChildren().add(step);

        GridPane.setConstraints(cnt, 0, 1);
        grid.getChildren().add(cnt);

        TextField name = new TextField();
        name.setPromptText("name");
        GridPane.setConstraints(name, 0, 2);
        grid.getChildren().add(name);

        TextField location = new TextField();
        location.setPromptText("location");
        GridPane.setConstraints(location, 0, 3);
        grid.getChildren().add(location);

        TextField firstYear = new TextField();
        firstYear.setPromptText("firstYear");
        GridPane.setConstraints(firstYear, 0, 4);
        grid.getChildren().add(firstYear);

        TextField anniversary = new TextField();
        anniversary.setPromptText("anniversary");
        GridPane.setConstraints(anniversary, 0, 5);
        grid.getChildren().add(anniversary);

        TextField significance = new TextField();
        significance.setPromptText("significance");
        GridPane.setConstraints(significance, 0, 6);
        grid.getChildren().add(significance);

        TextField figure = new TextField();
        figure.setPromptText("figure");
        GridPane.setConstraints(figure, 0, 7);
        grid.getChildren().add(figure);

        TextField event = new TextField();
        event.setPromptText("event");
        GridPane.setConstraints(event, 0, 8);
        grid.getChildren().add(event);

        TextField relic = new TextField();
        relic.setPromptText("relic");
        GridPane.setConstraints(relic, 0, 9);
        grid.getChildren().add(relic);


        GridPane btn = new GridPane();

        Button preview = new Button("Preview");
        preview.maxWidth(40);
        GridPane.setConstraints(preview, 0, 0);
        btn.getChildren().add(preview);
        Button clear = new Button("Clear");
        clear.maxWidth(40);
        GridPane.setConstraints(clear, 1, 0);
        btn.getChildren().add(clear);
        Button save = new Button("Save");
        save.maxWidth(40);
        save.setVisible(false);
        GridPane.setConstraints(save, 3, 0);
        btn.getChildren().add(save);

        GridPane.setConstraints(btn, 0, 10);
        grid.getChildren().add(btn);
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                anchorDataConfig.getChildren().clear();
                List<String> urls = new ArrayList<>();
                urls = checkTypeUrl(url.getText(), start.getText(), end.getText(), step.getText(), parentTag.getText());
                if (urls == null) {
                    return;
                }
                save.setVisible(true);

                HashMap<String, String> config = new HashMap<>();
                config.put("name", name.getText());
                config.put("location", location.getText());
                config.put("firstYear", firstYear.getText());
                config.put("anniversary", anniversary.getText());
                config.put("significance", significance.getText());
                config.put("figure", figure.getText());
                config.put("event", event.getText());
                config.put("relic", relic.getText());

                FestivalCrawler crawler = new FestivalCrawler();
                try {
                    List<Festival> data = crawler.crawlFestival(urls, config);
                    if (data.size() == 0) {
                        Label label = new Label();
                        label.setText("No data available");
                        label.setVisible(true);
                        anchorDataConfig.getChildren().add(label);
                    } else {
                        save.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    saveFestival(data);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                        int numberPage = data.size() % rowsPerPage() == 0 ? data.size() / rowsPerPage() : data.size() / rowsPerPage() + 1;
                        pagination = new Pagination(numberPage);
                        pagination.setPageFactory((Integer pageIndex) -> {
                            if (pageIndex > numberPage) {
                                return null;
                            } else {
                                return createFestivalPage(pageIndex, FXCollections.observableArrayList(data));
                            }
                        });

                        AnchorPane.setTopAnchor(pagination, 10.0);
                        AnchorPane.setRightAnchor(pagination, 10.0);
                        AnchorPane.setBottomAnchor(pagination, 10.0);
                        AnchorPane.setLeftAnchor(pagination, 10.0);
                        anchorDataConfig.getChildren().addAll(pagination);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.setText("");
                location.setText("");
                firstYear.setText("");
                anniversary.setText("");
                significance.setText("");
                figure.setText("");
                event.setText("");
                relic.setText("");
                url.setText("");
                parentTag.setText("");
                start.setText("");
                step.setText("");
                end.setText("");
            }
        });
    }

    private void showEventConfig() {
        TextField url = new TextField();
        url.setPromptText("url");
        url.setPrefColumnCount(5);
        GridPane.setConstraints(url, 0, 0);
        grid.getChildren().add(url);

        GridPane cnt = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField parentTag = new TextField();
        parentTag.setPromptText("Parent Tag");
        GridPane.setConstraints(parentTag, 0, 0);
        cnt.getChildren().add(parentTag);
        TextField start = new TextField();

        start.setPromptText("start");
        start.setMaxWidth(40);
        GridPane.setConstraints(start, 1, 0);
        cnt.getChildren().add(start);

        TextField end = new TextField();
        end.setPromptText("end");
        end.setMaxWidth(40);
        GridPane.setConstraints(end, 2, 0);
        cnt.getChildren().add(end);

        TextField step = new TextField();
        step.setPromptText("step");
        step.setMaxWidth(40);
        GridPane.setConstraints(step, 3, 0);
        cnt.getChildren().add(step);

        GridPane.setConstraints(cnt, 0, 1);
        grid.getChildren().add(cnt);

        TextField name = new TextField();
        name.setPromptText("name");
        GridPane.setConstraints(name, 0, 2);
        grid.getChildren().add(name);

        TextField time = new TextField();
        time.setPromptText("time");
        GridPane.setConstraints(time, 0, 3);
        grid.getChildren().add(time);

        TextField address = new TextField();
        address.setPromptText("address");
        GridPane.setConstraints(address, 0, 4);
        grid.getChildren().add(address);

        TextField result = new TextField();
        result.setPromptText("result");
        GridPane.setConstraints(result, 0, 5);
        grid.getChildren().add(result);

        TextField skateholders = new TextField();
        skateholders.setPromptText("skateholders");
        GridPane.setConstraints(skateholders, 0, 6);
        grid.getChildren().add(skateholders);


        GridPane btn = new GridPane();

        Button preview = new Button("Preview");
        preview.maxWidth(40);
        GridPane.setConstraints(preview, 0, 0);
        btn.getChildren().add(preview);
        Button clear = new Button("Clear");
        clear.maxWidth(40);
        GridPane.setConstraints(clear, 1, 0);
        btn.getChildren().add(clear);
        Button save = new Button("Save");
        save.maxWidth(40);
        save.setVisible(false);
        GridPane.setConstraints(save, 3, 0);
        btn.getChildren().add(save);

        GridPane.setConstraints(btn, 0, 7);
        grid.getChildren().add(btn);
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                anchorDataConfig.getChildren().clear();
                List<String> urls = new ArrayList<>();
                urls = checkTypeUrl(url.getText(), start.getText(), end.getText(), step.getText(), parentTag.getText());
                if (urls == null) {
                    return;
                }
                save.setVisible(true);

                HashMap<String, String> config = new HashMap<>();
                config.put("name", name.getText());
                config.put("time", time.getText());
                config.put("address", address.getText());
                config.put("result", result.getText());
                config.put("skateholders", skateholders.getText());

                EventCrawler crawler = new EventCrawler();
                try {
                    List<Event> data = crawler.crawlEvent(urls, config);
                    if (data.size() == 0) {
                        Label label = new Label();
                        label.setText("No data available");
                        label.setVisible(true);
                        anchorDataConfig.getChildren().add(label);
                    } else {
                        save.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    saveEvent(data);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                        int numberPage = data.size() % rowsPerPage() == 0 ? data.size() / rowsPerPage() : data.size() / rowsPerPage() + 1;
                        pagination = new Pagination(numberPage);
                        pagination.setPageFactory((Integer pageIndex) -> {
                            if (pageIndex > numberPage) {
                                return null;
                            } else {
                                return createEventPage(pageIndex, FXCollections.observableArrayList(data));
                            }
                        });

                        AnchorPane.setTopAnchor(pagination, 10.0);
                        AnchorPane.setRightAnchor(pagination, 10.0);
                        AnchorPane.setBottomAnchor(pagination, 10.0);
                        AnchorPane.setLeftAnchor(pagination, 10.0);
                        anchorDataConfig.getChildren().addAll(pagination);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.setText("");
                time.setText("");
                address.setText("");
                result.setText("");
                skateholders.setText("");
                url.setText("");
                parentTag.setText("");
                start.setText("");
                step.setText("");
                end.setText("");
            }
        });
    }

    private void showDynastyConfig() {
        TextField url = new TextField();
        url.setPromptText("url");
        url.setPrefColumnCount(5);
        GridPane.setConstraints(url, 0, 0);
        grid.getChildren().add(url);

        GridPane cnt = new GridPane();
        cnt.setVgap(3);
        cnt.setHgap(3);

        TextField parentTag = new TextField();
        parentTag.setPromptText("Parent Tag");
        GridPane.setConstraints(parentTag, 0, 0);
        cnt.getChildren().add(parentTag);
        TextField start = new TextField();

        start.setPromptText("start");
        start.setMaxWidth(40);
        GridPane.setConstraints(start, 1, 0);
        cnt.getChildren().add(start);

        TextField end = new TextField();
        end.setPromptText("end");
        end.setMaxWidth(40);
        GridPane.setConstraints(end, 2, 0);
        cnt.getChildren().add(end);

        TextField step = new TextField();
        step.setPromptText("step");
        step.setMaxWidth(40);
        GridPane.setConstraints(step, 3, 0);
        cnt.getChildren().add(step);

        GridPane.setConstraints(cnt, 0, 1);
        grid.getChildren().add(cnt);

        TextField name = new TextField();
        name.setPromptText("name");
        GridPane.setConstraints(name, 0, 2);
        grid.getChildren().add(name);

        TextField country = new TextField();
        country.setPromptText("country");
        GridPane.setConstraints(country, 0, 3);
        grid.getChildren().add(country);

        TextField ruleTime = new TextField();
        ruleTime.setPromptText("ruleTime");
        GridPane.setConstraints(ruleTime, 0, 4);
        grid.getChildren().add(ruleTime);

        TextField popularLanguage = new TextField();
        popularLanguage.setPromptText("popularLanguage");
        GridPane.setConstraints(popularLanguage, 0, 5);
        grid.getChildren().add(popularLanguage);

        TextField mainReligion = new TextField();
        mainReligion.setPromptText("mainReligion");
        GridPane.setConstraints(mainReligion, 0, 6);
        grid.getChildren().add(mainReligion);

        TextField government = new TextField();
        government.setPromptText("government");
        GridPane.setConstraints(government, 0, 7);
        grid.getChildren().add(government);

        TextField currencyUnit = new TextField();
        currencyUnit.setPromptText("currencyUnit");
        GridPane.setConstraints(currencyUnit, 0, 8);
        grid.getChildren().add(currencyUnit);


        TextField capital = new TextField();
        capital.setPromptText("capital");
        GridPane.setConstraints(capital, 0, 9);
        grid.getChildren().add(capital);

        TextField king = new TextField();
        king.setPromptText("king");
        GridPane.setConstraints(king, 0, 10);
        grid.getChildren().add(king);

        TextField event = new TextField();
        event.setPromptText("event");
        GridPane.setConstraints(event, 0, 11);
        grid.getChildren().add(event);

        GridPane btn = new GridPane();

        Button preview = new Button("Preview");
        preview.maxWidth(40);
        GridPane.setConstraints(preview, 0, 0);
        btn.getChildren().add(preview);
        Button clear = new Button("Clear");
        clear.maxWidth(40);
        GridPane.setConstraints(clear, 1, 0);
        btn.getChildren().add(clear);
        Button save = new Button("Save");
        save.maxWidth(40);
        save.setVisible(false);
        GridPane.setConstraints(save, 3, 0);
        btn.getChildren().add(save);

        GridPane.setConstraints(btn, 0, 12);
        grid.getChildren().add(btn);
        preview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                anchorDataConfig.getChildren().clear();
                List<String> urls = new ArrayList<>();
                urls = checkTypeUrl(url.getText(), start.getText(), end.getText(), step.getText(), parentTag.getText());
                if (urls == null) {
                    return;
                }
                save.setVisible(true);
                HashMap<String, String> config = new HashMap<>();
                HashMap<String, List<String>> configList = new HashMap<>();
                config.put("name", name.getText());
                config.put("country", country.getText());
                config.put("ruleTime", ruleTime.getText());
                config.put("popularLanguage", popularLanguage.getText());
                config.put("mainReligion", mainReligion.getText());
                config.put("government", government.getText());
                config.put("currencyUnit", currencyUnit.getText());

                configList.put("capital", Arrays.asList(capital.getText().split(",")));
                configList.put("king", Arrays.asList(king.getText().split(",")));
                configList.put("event", Arrays.asList(event.getText().split(",")));

                DynastyCrawler crawler = new DynastyCrawler();
                try {
                    List<Dynasty> data = crawler.crawlDynasty(urls, config, configList);
                    if (data.size() == 0) {
                        Label label = new Label();
                        label.setText("No data available");
                        label.setVisible(true);
                        anchorDataConfig.getChildren().add(label);
                    } else {
                        save.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    saveDynasty(data);
                                } catch (IllegalAccessException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                        int numberPage = data.size() % rowsPerPage() == 0 ? data.size() / rowsPerPage() : data.size() / rowsPerPage() + 1;
                        pagination = new Pagination(numberPage, 0);
                        pagination.setPageFactory((Integer pageIndex) -> {
                            if (pageIndex > numberPage) {
                                return null;
                            } else {
                                return createDynastyPage(pageIndex, FXCollections.observableArrayList(data));
                            }
                        });

                        AnchorPane.setTopAnchor(pagination, 10.0);
                        AnchorPane.setRightAnchor(pagination, 10.0);
                        AnchorPane.setBottomAnchor(pagination, 10.0);
                        AnchorPane.setLeftAnchor(pagination, 10.0);
                        anchorDataConfig.getChildren().addAll(pagination);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.setText("");
                url.setText("");
                parentTag.setText("");
                country.setText("");
                ruleTime.setText("");
                popularLanguage.setText("");
                mainReligion.setText("");
                government.setText("");
                currencyUnit.setText("");
                capital.setText("");
                king.setText("");
                event.setText("");
                start.setText("");
                step.setText("");
                end.setText("");
            }
        });
    }

    private VBox createRelicPage(int pageIndex, ObservableList<Relic> data) {
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

    public VBox createFigurePage(int pageIndex, ObservableList<Figure> data) {
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

    public VBox createEventPage(int pageIndex, ObservableList<Event> data) {
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

    public VBox createDynastyPage(int pageIndex, ObservableList<Dynasty> data) {
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

    public VBox createFestivalPage(int pageIndex, ObservableList<Festival> data) {
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

    private List<String> checkTypeUrl(String url, String start, String end, String step, String parentTag) {
        List<String> urls = new ArrayList<>();
        if (url.isEmpty()) {
            Label label = new Label();
            label.setText("Please fill the URL");
            label.setVisible(true);
            anchorDataConfig.getChildren().add(label);
            return null;
        } else if (!start.isEmpty() && !end.isEmpty() && !step.isEmpty() && !parentTag.isEmpty()) {
            if (!url.contains("=%s")) {
                Label label = new Label();
                label.setText("Missing %s in String" + url);
                label.setVisible(true);
                anchorDataConfig.getChildren().add(label);
                return null;
            } else {
                urls = Scrap.scrapUrlv2(url,start, end, step, parentTag);
            }
        } else if (!parentTag.isEmpty()) {
            urls = Scrap.scrapUrl(url, parentTag);
        } else {
            urls.add(url);
        }
        return urls;
    }
}
