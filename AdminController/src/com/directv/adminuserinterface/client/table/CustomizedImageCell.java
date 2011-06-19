/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.table;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomizedImageCell.
 */
public class CustomizedImageCell extends AbstractSafeHtmlCell<String> {

	/**
	 * The Interface Template.
	 */
	interface Template extends SafeHtmlTemplates {

		/**
		 * Img.
		 *
		 * @param url the url
		 * @return the safe html
		 */
		@Template("<img src=\"{0}\"/>")
		SafeHtml img(String url);
	}

	/** The template. */
	private static Template template;

	/**
	 * Instantiates a new customized image cell.
	 */
	public CustomizedImageCell() {
		this(SimpleSafeHtmlRenderer.getInstance());
		if (template == null) {
			template = GWT.create(Template.class);
		}
	}

	/**
	 * Instantiates a new customized image cell.
	 *
	 * @param renderer the renderer
	 */
	public CustomizedImageCell(SafeHtmlRenderer<String> renderer) {
		super(renderer, "click", "keydown");
	}

	/**
	 * Overridden Method
	 * @param context
	 * @param parent
	 * @param value
	 * @param event
	 * @param valueUpdater
	 */
	@Override
	public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			if (!Element.is(eventTarget)) {
				return;
			}
			if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
				// Ignore clicks that occur outside of the main element.
				onEnterKeyDown(context, parent, value, event, valueUpdater);
			}
		}
	}

	/**
	 * Overridden Method
	 * @param context
	 * @param data
	 * @param sb
	 */
	@Override
	public void render(Context context, SafeHtml data, SafeHtmlBuilder sb) {
		sb.append(template.img(data.asString()));
	}

	/**
	 * Overridden Method
	 * @param context
	 * @param parent
	 * @param value
	 * @param event
	 * @param valueUpdater
	 */
	@Override
	protected void onEnterKeyDown(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
		if (valueUpdater != null) {
			valueUpdater.update(value);
		}
	}
}
