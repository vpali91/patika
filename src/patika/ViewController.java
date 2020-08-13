package patika;
import java.util.Collections;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ViewController implements Initializable {

    @FXML
    TableView table;
    @FXML
    TextField inputGyogy;
    @FXML
    TextField inputMennyiseg;
    @FXML
    TextField inputHaszn;
    @FXML
    TextField inputEllen;
    @FXML
    DatePicker inputDate;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    SplitPane mainSplit;
    @FXML
    AnchorPane anchor;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;

    DB db = new DB();
    private final String MENU_CONTACTS = "Gyógyszerek";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    private final int HET = 7;
    private static final LocalDate MIN = LocalDate.now();

//list amibe az input kerül    
    private final ObservableList<Gyogyszer> data = FXCollections.observableArrayList();
    
   
    @FXML
   @SuppressWarnings("ObjectEqualsNull")
   //input hozzáadaása a táblázathoz és az adatbázishoz
    private void addGyogyszer(ActionEvent event) {
            LocalDate datum = inputDate.getValue();
            String gyogynev = inputGyogy.getText();
            String mennyi = inputMennyiseg.getText();
            String haszn = inputHaszn.getText();
            String ellenj = inputEllen.getText();
            
            if (mennyi != null && isNumeric(mennyi)){
            if (datum != null && datum.isAfter(MIN)){
            Gyogyszer ujGyogyszer = new Gyogyszer(inputGyogy.getText(), inputMennyiseg.getText(), inputHaszn.getText(), inputEllen.getText(), inputDate.getValue().format(DateTimeFormatter.ISO_DATE));
            try{
            if( !gyogynev.equals("") && !mennyi.equals("") &&  !haszn.equals("") && !ellenj.equals("") && datum != null){
            data.add(ujGyogyszer);
            db.addGyogyszer(ujGyogyszer);
            inputGyogy.clear();
            inputMennyiseg.clear();
            inputHaszn.clear();
            inputEllen.clear();
                
           }else{
                    alert1("Minden mezőt ki kell tölteni!");
                }
            }catch (NullPointerException e){
            alert (e.getMessage());
            }
            
                }else{
                alert("Lejárt a szavatossága, vagy üresen adta meg a dátum mezőt!");
    }
    }else{
            alert2("Számot kell megadni a mennyiség mezőbe, vagy üresen adta meg a dátum mezőt!");    
            }
    }
    
    public static boolean isNumeric(String str)
{
  NumberFormat formatter = NumberFormat.getInstance();
  ParsePosition pos = new ParsePosition(0);
  formatter.parse(str, pos);
  return str.length() == pos.getIndex();
}
    
    @FXML
    private void exportList(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, data);
            inputExportName.clear();
        }else{
            alert("Adj meg egy fájlnevet!");
        }
    }

    public void setTableData() {
        
      //gyógyszer oszlop beáll  
        TableColumn gyogyCol = new TableColumn("Gyógyszer");
        gyogyCol.setMinWidth(130);
        gyogyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        gyogyCol.setCellValueFactory(new PropertyValueFactory<Gyogyszer, String>("gyogyszer"));

        gyogyCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Gyogyszer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Gyogyszer, String> t) {
                Gyogyszer actualGyogyszer = (Gyogyszer) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualGyogyszer.setGyogyszer(t.getNewValue());
                db.updateGyogyszer(actualGyogyszer);
            }
        }
        );
// mennyiseg oszlop beáll
        TableColumn mennyisegCol = new TableColumn("Mennyiség");
        mennyisegCol.setMinWidth(130);
        mennyisegCol.setCellFactory(TextFieldTableCell.forTableColumn());
        mennyisegCol.setCellValueFactory(new PropertyValueFactory<Gyogyszer, String>("mennyiseg"));

        mennyisegCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Gyogyszer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Gyogyszer, String> t) {
                Gyogyszer actualGyogyszer = (Gyogyszer) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualGyogyszer.setMennyiseg(t.getNewValue());
                db.updateGyogyszer(actualGyogyszer);
            }
        }
        );
// Ellenjavallat oszlop beáll
        TableColumn hasznalCol = new TableColumn("Használat");
        hasznalCol.setMinWidth(250);
        hasznalCol.setCellValueFactory(new PropertyValueFactory<Gyogyszer, String>("hasznalat"));
        hasznalCol.setCellFactory(TextFieldTableCell.forTableColumn());
// Ellenjavallat oszlop értékadás
        hasznalCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Gyogyszer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Gyogyszer, String> t) {
                Gyogyszer actualGyogyszer = (Gyogyszer) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualGyogyszer.setHasznalat(t.getNewValue());
                db.updateGyogyszer(actualGyogyszer);
             }
        }
        );
         // Ellenjavallat oszlop beáll
        TableColumn ellenCol = new TableColumn("Ellenjavallat");
        ellenCol.setMinWidth(130);
        ellenCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ellenCol.setCellValueFactory(new PropertyValueFactory<Gyogyszer, String>("ellenJav"));
// Ellenjavallat oszlop értékadás
        ellenCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Gyogyszer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Gyogyszer, String> t) {
                Gyogyszer actualGyogyszer = (Gyogyszer) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualGyogyszer.setEllenJav(t.getNewValue());
                db.updateGyogyszer(actualGyogyszer);
            }
        }
        );
        // szavatosság oszlop beáll
        TableColumn datumCol = new TableColumn("Szavatosság");
        datumCol.setMinWidth(130);
        datumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        datumCol.setCellValueFactory(new PropertyValueFactory<Gyogyszer, String>("szavatossag"));
// szavatosság oszlop értékadás
        datumCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Gyogyszer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Gyogyszer, String> t) {
                Gyogyszer actualGyogyszer = (Gyogyszer) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualGyogyszer.setSzavatossag(t.getNewValue());
                db.updateGyogyszer(actualGyogyszer);
                   }
            }
        );
        // Törlés gomb és törölje a sort
        TableColumn removeCol = new TableColumn( "Törlés" );
        hasznalCol.setMinWidth(100);

        Callback<TableColumn<Gyogyszer, String>, TableCell<Gyogyszer, String>> cellFactory = 
                new Callback<TableColumn<Gyogyszer, String>, TableCell<Gyogyszer, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Gyogyszer, String> param )
                    {
                        final TableCell<Gyogyszer, String> cell = new TableCell<Gyogyszer, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Gyogyszer gyogyszer = getTableView().getItems().get( getIndex() );
                                                data.remove(gyogyszer);
                                                db.removeGyogyszer(gyogyszer);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };
        

        removeCol.setCellFactory( cellFactory );
        
        // beállítja az oszlopokat, hozzáadja a táblázathoz és az adatbázishoz
        table.getColumns().addAll(gyogyCol, mennyisegCol, hasznalCol, ellenCol, datumCol, removeCol);
        table.setItems(data);
        data.addAll(db.getAllGyogyszer());
  
         final ObservableList<Integer> highlightRows = FXCollections.observableArrayList();

    table.setRowFactory(new Callback<TableView<Gyogyszer>, TableRow<Gyogyszer>>() {
       
        @Override
        public TableRow<Gyogyszer> call(TableView<Gyogyszer> tableView) {
            final TableRow<Gyogyszer> row = new TableRow<Gyogyszer>() {
                @Override
                protected void updateItem(Gyogyszer gyogyszer, boolean empty){
                    super.updateItem(gyogyszer, empty);
                    if (highlightRows.contains(getIndex())) {
                        if (! getStyleClass().contains(0)) {
                            getStyleClass().add("highlightedRow");
                        }
                    } else {
                        getStyleClass().removeAll(Collections.singleton("highlightedRow"));
                    }
                }
            };
            highlightRows.addListener(new ListChangeListener<Integer>() {
                @Override
                public void onChanged(Change<? extends Integer> change) {
                    if (highlightRows.contains(row.getIndex())) {
                        if (! row.getStyleClass().contains("highlightedRow")) {
                            row.getStyleClass().add("highlightedRow");
                        }
                    } else {
                        row.getStyleClass().removeAll(Collections.singleton("highlightedRow"));
                    }
                }
            });
            return row;
        }
    });
     
    }
    
    // beállítja a menüoszlopot, azokra kattintva lehet váltani a lista, export és kilépés
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);

        nodeItemA.setExpanded(true);

        Node contactsNode = new ImageView();
        Node exportNode = new ImageView();
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST, contactsNode);
        TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT, exportNode);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });

    }
//Hibaüzenetet generál
    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }
    //Hibaüzenetet generál
     private void alert1(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }
     
      //Hibaüzenetet generál
     private void alert2(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setMenuData();
    }

    
}