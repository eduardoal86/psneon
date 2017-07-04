package edualves.com.psneon.history.ui;

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
import edualves.com.psneon.model.TransferResponse;

/**
 * Created by edualves on 03/07/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<TransferResponse> transferList;
    private List<ContactInfoResponse> contacts;
    private Context context;

    public HistoryAdapter(Context context,
                          List<TransferResponse> transferList) {
        this.context = context;
        this.transferList = transferList;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.transfer_list_item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder holder, int position) {

        for (ContactInfoResponse contactInfo : contacts) {

            if (transferList.get(position).getClienteId() == contactInfo.getId()) {

                holder.transferName.setText(contactInfo.getName());
                holder.transferPhone.setText(contactInfo.getPhone());

                Glide.with(context)
                .load(contactInfo.getUrl())
                .into(holder.transferImage);
            }

        }

        holder.transferValue.setText(transferList.get(position).getValor().toString());

    }

    @Override
    public int getItemCount() {
        return transferList.size();
    }

    public void setContactList(List<ContactInfoResponse> contacts) {
        this.contacts = contacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transferName;
        ImageView transferImage;
        TextView transferPhone;
        TextView transferValue;

        public ViewHolder(View itemView) {
            super(itemView);
            transferName = (TextView) itemView.findViewById(R.id.transfer_name);
            transferImage = (ImageView) itemView.findViewById(R.id.transfer_photo);
            transferPhone = (TextView) itemView.findViewById(R.id.transfer_phone);
            transferValue = (TextView) itemView.findViewById(R.id.transfer_value);
        }

    }
}
