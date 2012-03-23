package edu.cmu.mobility.charm;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import edu.cmu.mobility.charm.data.AttributeValues;

public class AttributeLabelActivity extends Activity {
	
	private static ListView attributeListView;
	private static SimpleAdapter attributeListAdapter;
	private static ArrayList<HashMap<String,String>> attributeList;
	private static AttributeValues attributeValues;
	
	private static final String ATTRIBUTE_TYPE = "AttributeType";
	private static final String ATTRIBUTE_VALUE = "AttributeValue";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_label_activity);
        initUserInterface();
        
        attributeValues = AttributeValues.getInstance();
        
        initAttributeListView();
	}
	
	private void initUserInterface () {
		attributeListView = (ListView) this.findViewById(R.id.listAttributes);
	}
	
	private void initAttributeListView() {
		attributeList = new ArrayList<HashMap<String,String>>();
		attributeListAdapter = new SimpleAdapter(this, attributeList,
    			//R.layout.attribute_list,
    			android.R.layout.simple_list_item_multiple_choice,
                //android.R.layout.simple_list_item_2, 
                new String[] { ATTRIBUTE_TYPE },
                //new int[] { R.id.checkBox1 }
                new int[] { android.R.id.text1 }
		);
		
	    
    	updateAttributeValues();
    	
    	attributeListView.setAdapter(attributeListAdapter);
    	attributeListView.setTextFilterEnabled(true);
    	attributeListView.setItemsCanFocus(false);
    	attributeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
	
	public static void updateAttributeValues () {
		attributeList.clear();
		String [] dataTypeStrings = attributeValues.getAttributeTypeStrings();
		double [] dataValues = attributeValues.getAttributeValues();
		
		for (int i=0; i<dataValues.length; i++) {
	    	HashMap<String,String> row = new HashMap<String,String>();
	    	row.put(ATTRIBUTE_TYPE, dataTypeStrings[i]);
	    	row.put(ATTRIBUTE_VALUE, String.format("%.1f", dataValues[i]) );
	    	//row.put(SENSOR_DATA_VALUE, new DecimalFormat("#.##").format(dataValues[i]) );
	    	attributeList.add(row);
		}
		attributeListAdapter.notifyDataSetChanged();
    }
}
