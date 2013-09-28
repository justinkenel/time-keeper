package com.timekeep.front;

import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class EditableRowTablePresenter<T> {
  public final JPanel view;
  private final DefaultTableModel tableModel;
  private final EntityToRowConverter<T> entityToRowConverter;
  private final RowChangeHandler<T> rowChangeHandler;
  private final Vector<String> headers;

  private EditableRowTablePresenter(JPanel view, DefaultTableModel tableModel,
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
    private List<K> values;
    private List<String> headers;

    private Builder(EntityToRowConverter<K> entityToRowConverter,
                    RowChangeHandler<K> rowChangeHandler,
                    RowCellEditorCreator<K> rowCellEditorCreator,
                    List<K> values,
                    List<String> headers) {
      this.entityToRowConverter = entityToRowConverter;
      this.rowChangeHandler = rowChangeHandler;
      this.rowCellEditorCreator = rowCellEditorCreator;
      this.values = values;
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

    public Builder<K> setValues(List<K> values) {
      this.values = values;
      return this;
    }

    public EditableRowTablePresenter<K> build() {
      DefaultTableModel model = new DefaultTableModel();
      JTable jtable = new JTable(model);

      Vector<String> headerVector = new Vector<String>(headers.size());
      for (String header : headers) {
        headerVector.add(header);
      }

      model.setDataVector(new Vector(0), headerVector);

      //
      final Vector<Vector<String>> rowData = new Vector<Vector<String>>();
      for (K value : values) {
        rowData.add(entityToRowConverter.convertToRow(value));
      }
      model.setDataVector(rowData, headerVector);

      model.addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent event) {
          int index = event.getFirstRow();
          if (index >= 0) {
            Vector<String> data = rowData.elementAt(index);
            rowChangeHandler.handleRowChange(index, data);
          }
        }
      });


      //

      JScrollPane scrollPane = new JScrollPane(jtable);
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

      JPanel view = FillComponent.verticalFillBuilder().
          addCalculatedComponent(scrollPane).
          build();

      TableCellEditor[] editors = rowCellEditorCreator.getRowCellEditors();
      for (int i = 0; i < editors.length; ++i) {
        if (editors[i] != null) {
          jtable.getColumnModel().getColumn(i).setCellEditor(editors[i]);
        }
      }

      return new EditableRowTablePresenter<K>(view, model, entityToRowConverter,
          rowChangeHandler, rowCellEditorCreator, headerVector);
    }
  }

  public static <T> Builder<T> builder() {
    return new Builder<T>(null,
        new DefaultRowChangeHandler<T>(),
        new DefaultRowCellEditorCreator<T>(),
        new LinkedList<T>(),
        new LinkedList<String>());
  }

  public void setValues(Iterable<T> values) {
    final Vector<Vector<String>> rowData = new Vector<Vector<String>>();
    for (T value : values) {
      rowData.add(entityToRowConverter.convertToRow(value));
    }
    tableModel.setDataVector(rowData, headers);

    tableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent event) {
        int index = event.getFirstRow();
        if (index >= 0) {
          Vector<String> data = rowData.elementAt(index);
          rowChangeHandler.handleRowChange(index, data);
        }
      }
    });
  }

  public static interface EntityToRowConverter<K> {
    Vector<String> convertToRow(K entity);
  }

  public static interface RowChangeHandler<K> {
    void handleRowChange(int index, Vector<String> row);
  }

  private static class DefaultRowChangeHandler<K> implements RowChangeHandler<K> {
    @Override
    public void handleRowChange(int index, Vector<String> row) {
    }
  }

  public static interface RowCellEditorCreator<K> {
    TableCellEditor[] getRowCellEditors();
  }

  private static class DefaultRowCellEditorCreator<K> implements RowCellEditorCreator<K> {
    @Override
    public TableCellEditor[] getRowCellEditors() {
      return new TableCellEditor[0];
    }
  }
}
