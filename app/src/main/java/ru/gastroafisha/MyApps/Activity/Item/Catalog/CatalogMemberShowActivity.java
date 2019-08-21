package ru.gastroafisha.MyApps.Activity.Item.Catalog;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.gastroafisha.MyApps.Activity.CommonShowActivity;
import ru.gastroafisha.MyApps.MainApplication;
import ru.gastroafisha.MyApps.Model.Item.CatalogMemberFavoriteModel;
import ru.gastroafisha.MyApps.R;
import ru.gastroafisha.MyApps.Utils.ImageUtils;
import ru.gastroafisha.MyApps.Utils.StringUtils;

public class CatalogMemberShowActivity extends CommonShowActivity<CatalogMemberFavoriteModel> {

    @Override
    protected Integer getLayoutId() {
        return R.layout.content_catalog_member_show;
    }

    @Override
    protected Flowable<CatalogMemberFavoriteModel> loadItem(Integer catalogId, Integer itemId) {
        return ((MainApplication) getApplication()).getLocalDataStorage().getCatalogMembers().get(getOrgId(), catalogId, itemId);
    }

    @Override
    protected void fillItem(CatalogMemberFavoriteModel item) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(item.getItem().getName());

        // Image
        ImageView image = (ImageView) findViewById(R.id.item_photo);
        getFormDisposable().add(ImageUtils.setTemporaryImage(getOrgId(), item.getItem().getPhoto(), image));

        // Name
        TextView name = (TextView) findViewById(R.id.item_name);
        name.setText(StringUtils.textToHtml(item.getItem().getName()));

        // Text
        TextView text = (TextView) findViewById(R.id.item_text);
        text.setText(StringUtils.textToHtml(item.getItem().getText()));

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup contactsHolder = (ViewGroup) findViewById(R.id.contacts_holder);

        // Stand
        if (item.getItem().getStand() != null && !item.getItem().getStand().isEmpty()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_stand, null, item.getItem().getStand());
        }

        // Categories
        for (String c : item.getItem().getCategories()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_category, null, c);
        }

        // Phones
        for (String c : item.getItem().getPhones()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_phone, "tel:", c);
        }

        // Emails
        for (String c : item.getItem().getEmails()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_email, "mailto:", c);
        }

        // Sites
        for (String c : item.getItem().getSites()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_site, "", c);
        }

        // VKs
        for (String c : item.getItem().getVks()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_vk, "", c);
        }

        // FBs
        for (String c : item.getItem().getFbs()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_fb, "", c);
        }

        // Insts
        for (String c : item.getItem().getInsts()) {
            addContact(inflater, contactsHolder, R.string.caption_contact_inst, "", c);
        }
    }

    private void addContact(LayoutInflater inflater, ViewGroup contactsHolder, Integer caption, String type, String value) {
        TextView contact = (TextView)inflater.inflate(R.layout.view_contact, contactsHolder, false);

        String v;
        String c = StringUtils.textFormat(caption);

        if (type != null) {
            v = String.format("%s: <a href=\"%s\">%s</a>", c, type + value, value);
        } else {
            v = String.format("%s: %s", c, value);
        }

        contact.setText(StringUtils.textToHtml(v));
        contactsHolder.addView(contact);
    }
}
