package eu.cloudopting.ui.ToscaUI.client.controller.datasource;

import java.util.List;

import org.cruxframework.crux.core.client.datasource.LocalPagedDataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSource;
import org.cruxframework.crux.core.client.datasource.annotation.DataSourceRecordIdentifier;

import eu.cloudopting.ui.ToscaUI.server.model.RowCustomizationDTO;

@DataSource("rowCustomizationDataSource")
@DataSourceRecordIdentifier("idCustomization")
public class RowCustomizationDataSource extends LocalPagedDataSource<RowCustomizationDTO> {

	private List<RowCustomizationDTO> rows;
	
	public List<RowCustomizationDTO> getRows() {
		return rows;
	}
	
	public void setRows(List<RowCustomizationDTO> rows) {
		this.rows = rows;
	}
	
	@Override
	public void load() {
		updateData(getRows());		
	}
	
}
