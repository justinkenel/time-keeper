package com.timekeep.front;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class EditableRowTablePresenter<T> {
  public final JTable view;
  private final DefaultTableModel tableModel;
  private final EntityToRowConverter<T> entityToRowConverter;
  private final RowChangeHandler<T> rowChangeHandler;
  private final Vector<String> headers;

  private EditableRowTablePresenter(JTable view, DefaultTableModel tableModel,
                                    EntityToRowConverter<T> entityToRowConverter,
                                    RowChangeHandler<T> rowChangeHandler,
                                    RowCellEditorCreator<T> rowCellEditorCreator,
                                    Vector<String> headers) {
    this.view = view;
    this.tableModel = tableModel;
    this.entityToRowConverter = entityToRowConverter;
    this.rowChangeHandler = rowChangeHandler;
    this.headers = headers;
  }

  public static class Builder<K> {
    private EntityToRowConverter<K> entityToRowConverter;
    private RowChangeHandler<K> rowChangeHandler;
    private RowCellEditorCreator<K> rowCellEditorCreator;
    private List<String> headers;

    private Builder(EntityToRowConverter<K> entityToRowConverter,
                    RowChangeHandler<K> rowChangeHandler,
                    RowCellEditorCreator<K> rowCellEditorCreator,
                    List<String> headers) {
      this.entityToRowConverter = entityToRowConverter;
      this.rowChangeHandler = rowChangeHandler;
      this.rowCellEditorCreator = rowCellEditorCreator;
      this.headers = headers;
    }

    public Builder<K> setEntityToRowConverter(EntityToRowConverter<K> converter) {
      this.entityToRowConverter = converter;
      return this;
    }

    public Builder<K> setRowChangeHandler(RowChangeHandler<K> rowChangeHandler) {
      this.rowChangeHandler = rowChangeHandler;
      return this;
    }

    public Builder<K> setRowCellEditorCreator(RowCellEditorCreator<K> rowCellEditorCreator) {
      this.rowCellEditorCreator = rowCellEditorCreator;
      return this;
    }

    public Builder<K> addHeader(String header) {
      this.headers.add(header);
      return this;
    }

    public EditableRowTablePresenter<K> build() {
      DefaultTableModel model = new DefaultTableModel();
      JTable jtable = new JTable(model);

      Vector<String> headerVector = new Vector<String>(headers.size());
      for(String header : headers) {
        headerVector.add(header);
      }

      return new EditableRowTablePresenter<K>(jtable, model, entityToRowConverter,
          rowChangeHandler, rowCellEditorCreator, headerVector);
    }
  }

  public static <T> Builder<T> builder() {
    return new Builder<T>(null, null, null, new LinkedList<String>());
  }

  public void setValues(Iterable<T> values) {
    final Vector<String[]> rowData = new Vector<String[]>();
    for(T value : values) {
      rowData.add(entityToRowConverter.convertToRow(value));
    }
    tableModel.setDataVector(rowData, headers);

    tableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent event) {
        int index = event.getFirstRow();
        String[] data = rowData.elementAt(index);
        rowChangeHandler.handleRowChange(data);
      }
    });
  }

  public static interface EntityToRowConverter<K> {
    String[] convertToRow(K entity);
  }

  public static interface RowChangeHandler<K> {
    void handleRowChange(String[] row);
  }

  public static interface RowCellEditorCreator<K> {
    CellEditor[] getRowCellEditors();
  }
}
