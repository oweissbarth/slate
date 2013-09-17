package de.oweissbarth.slate.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.oweissbarth.slate.R;
import de.oweissbarth.slate.data.Camera;
import de.oweissbarth.slate.data.Lens;
import de.oweissbarth.slate.data.Media;

public class ThreePosListAdapter extends BaseAdapter {
	private Object[] items;
	private Context context;
	private int section;
	
	public ThreePosListAdapter(Context context, Object[] items, int section){
		this.context=context;
		this.items=items;
		this.section=section;
	}
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int id) {
		if (id< items.length)
			return this.items[id];
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
			if(view==null){
				LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.three_pos_list_item, null);
			}
			
			TextView left = (TextView)view.findViewById(R.id.three_pos_list_item_left);
			TextView center = (TextView)view.findViewById(R.id.three_pos_list_item_center);
			TextView right = (TextView)view.findViewById(R.id.three_pos_list_item_right);
			
			Object item = this.items[position];
			
			switch(this.section){
				case 0: Camera camera = (Camera)item;
						left.setText(String.valueOf(camera.getId()));
						center.setText(camera.getName());
						right.setText("");
						break;
				
				case 1: Lens lens = (Lens)item;
						left.setText(String.valueOf(lens.getId()+"\t"+lens.getName()));
						center.setText("");
						String focalLength = lens.getFixed()? lens.getMinFocalLength()+"mm" : lens.getMinFocalLength()+" mm-" + lens.getMaxFocalLength()+"mm";
						right.setText(focalLength);
						break;
						
				case 2: Media media = (Media)item;
						left.setText(String.valueOf(media.getId()));
						center.setText(media.getName());
						right.setText(media.getStorageString());
						break;
			}
			return view;
	}

}
