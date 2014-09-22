package ar.com.mulesoft.nicecopyandpaste.ui.util;

import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Some utils methods to work with TableViewer.
 * 
 * @author ldebello
 */
public class TableViewerHelper {
	public static void addFixedColumn(TableViewer tableViewer, AbstractColumnLayout layout, String header, int widthInPixels) {		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		layout.setColumnData(tableColumn, new ColumnPixelData(widthInPixels, true));
		tableColumn.setText(header);
	}
	
	public static void addWeightColumn(TableViewer tableViewer, AbstractColumnLayout layout, String header, int weight) {		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		layout.setColumnData(tableColumn, new ColumnWeightData(weight, ColumnWeightData.MINIMUM_WIDTH, true));
		tableColumn.setText(header);
	}
}
