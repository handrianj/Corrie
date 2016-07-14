package org.handrianj.corrie.hermes.ui.tableviewer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.handrianj.corrie.hermes.datamodel.CellData;
import org.handrianj.corrie.hermes.datamodel.ExcelSheet;
import org.handrianj.corrie.utilsui.TableBuilerHelper;

/**
 * Class used to build excel table
 *
 * @author Heri Andrianjafy
 *
 */
public class ExcelTableContentManager {

	private TableViewer tableViewer;
	private ExcelSheet excelSheet;
	private DefaultExcelComparator comparator;
	private boolean comparatorDefaultActive;
	private ExcelTableColorProvider backgroundColorProvider;
	private ExcelTableColorProvider foregroundColorProvider;

	/**
	 * Constructor with just parent
	 *
	 * @param parent
	 */
	public ExcelTableContentManager(Composite parent) {
		this(parent, true);
	}

	/**
	 * Constructor with parent and boolean to indicate if the comparator should
	 * be active
	 *
	 * @param parent
	 * @param defaultActiveComparator
	 */
	public ExcelTableContentManager(Composite parent, boolean defaultActiveComparator) {
		tableViewer = TableBuilerHelper.buildTableViewer(parent);
		tableViewer.setContentProvider(new ExcelTableContentProvider());
		comparatorDefaultActive = defaultActiveComparator;
	}

	/**
	 * Sets the input of the table ( must be an excell sheet)
	 *
	 * @param excelSheet
	 */
	@SuppressWarnings("serial")
	public void setInput(ExcelSheet excelSheet) {

		this.excelSheet = excelSheet;
		TableColumn[] tablecolumn = tableViewer.getTable().getColumns();
		for (TableColumn CurrentColumn : tablecolumn) {
			CurrentColumn.dispose();
		}
		Collection<String> alltitles = excelSheet.getTitles();
		List<TableViewerColumn> colums = new ArrayList<>();
		comparator = new DefaultExcelComparator(new ArrayList<>(alltitles));
		comparator.setActive(comparatorDefaultActive);
		int colIndex = 0;
		for (String title : alltitles) {

			colums.add(TableBuilerHelper.addColumn(title, colIndex++, TableBuilerHelper.DEFAULT_COLUMN_SIZE,
					new ColumnLabelProvider() {
						@Override
						public String getText(Object excelSheet) {
							@SuppressWarnings("rawtypes")
							Map excelRow = (Map) excelSheet;

							CellData data = (CellData) excelRow.get(title);

							if (data == null) {
								return "";
							}

							Class<?> type = ExcelTableContentManager.this.excelSheet.getType(title);
							if (type.isAssignableFrom(Double.class)) {
								Double doubleValue = Double.valueOf(data.getValue());
								DecimalFormatSymbols symbols = new DecimalFormatSymbols();
								symbols.setGroupingSeparator(' ');

								DecimalFormat format = new DecimalFormat("#,###.######", symbols);

								return format.format(doubleValue);
							} else {
								return data.getValue();
							}
						}

						@Override
						public Color getBackground(Object element) {

							if (backgroundColorProvider == null) {
								return super.getBackground(element);
							} else {
								return backgroundColorProvider.getColor(title, element);
							}
						}

						@Override
						public Color getForeground(Object element) {
							if (foregroundColorProvider == null) {
								return super.getBackground(element);
							} else {
								return foregroundColorProvider.getColor(title, element);
							}
						}

					}, tableViewer, comparator));

		}

		tableViewer.setComparator(comparator);
		tableViewer.setInput(excelSheet);
		tableViewer.getTable().getParent().layout();

	}

	/**
	 * Sets focus on table
	 *
	 * @return
	 */
	public boolean setFocus() {
		return tableViewer.getTable().setFocus();
	}

	/**
	 * Returns the excelsheet used by the table
	 *
	 * @return
	 */
	public ExcelSheet getInput() {
		return excelSheet;
	}

	/**
	 * Returs the currently used comparator
	 *
	 * @return
	 */
	public DefaultExcelComparator getComparator() {
		return comparator;
	}

	/**
	 * Sets a background provider for all the table cells
	 *
	 * @param backgroundColorProvider
	 */
	public void setBackgroundColorProvider(ExcelTableColorProvider backgroundColorProvider) {
		this.backgroundColorProvider = backgroundColorProvider;
	}

	/**
	 * Sets the foreground for all the table cells
	 * 
	 * @param foregroundColorProvider
	 */
	public void setForegroundColorProvider(ExcelTableColorProvider foregroundColorProvider) {
		this.foregroundColorProvider = foregroundColorProvider;
	}

}
