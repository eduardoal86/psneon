package edualves.com.psneon.contacts.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edualves.com.psneon.R;
import edualves.com.psneon.model.ContactInfoResponse;

/**
 * Created by edualves on 01/07/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private List<ContactInfoResponse> contactList;
    private Context context;

    public ContactAdapter(Context context,
                         List<ContactInfoResponse> contactList,
                         OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.contact_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactAdapter.ViewHolder holder, int position) {
        holder.onClick(contactList.get(position), listener);

        holder.contactName.setText(contactList.get(position).getName());
        holder.contactPhone.setText(contactList.get(position).getPhone());

        Glide.with(context)
                .load(contactList.get(position).getUrl())
                //.placeholder(android.R.drawable.star_on)
                .into(holder.contactImage);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface OnItemClickListener {
        void onClick(ContactInfoResponse contactItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        ImageView contactImage;
        TextView contactPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            contactImage = (ImageView) itemView.findViewById(R.id.contact_photo);
            contactPhone = (TextView) itemView.findViewById(R.id.contact_phone);
        }

        public void onClick(final ContactInfoResponse contact,
                            final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(contact);
                }
            });
        }

    }
}
