package eu.cloudopting.ui.ToscaUI.client.controller.datasource;

import java.util.List;

import org.cruxframework.crux.core.client.datasource.LocalPagedDataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSourceRecordIdentifier;

import eu.cloudopting.ui.ToscaUI.server.model.RowDTO;

@DataSource("rowDataSource")
@DataSourceRecordIdentifier("id")
public class RowDataSource extends LocalPagedDataSource<RowDTO> {

	private List<RowDTO> rows;
	
	public List<RowDTO> getRows() {
		return rows;
	}
	
	public void setRows(List<RowDTO> rows) {
		this.rows = rows;
	}
	
	@Override
	public void load() {
		updateData(getRows());		
	}
	
}
