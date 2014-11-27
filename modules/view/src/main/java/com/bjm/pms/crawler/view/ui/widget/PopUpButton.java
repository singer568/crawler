/*
 * aTunes 1.12.0
 * Copyright (C) 2006-2009 Alex Aranda, Sylvain Gaudard, Thomas Beckers and contributors
 *
 * See http://www.atunes.org/wiki/index.php?title=Contributing for information about contributors
 *
 * http://www.atunes.org
 * http://sourceforge.net/projects/atunes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.bjm.pms.crawler.view.ui.widget;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import com.bjm.pms.crawler.view.ui.style.GuiUtils;



public class PopUpButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5193978267971626102L;

	/** The Constant TOP_LEFT. */
	public static final int TOP_LEFT = 0;

	/** The Constant TOP_RIGHT. */
	public static final int TOP_RIGHT = 1;

	/** The Constant BOTTOM_LEFT. */
	public static final int BOTTOM_LEFT = 3;

	/** The Constant BOTTOM_RIGHT. */
	public static final int BOTTOM_RIGHT = 4;

	/** The menu. */
	JPopupMenu menu;

	/** The object. */
	PopUpButton object;

	/** The location. */
	private int location;

	/** The x location. */
	int xLocation;

	/** The y location. */
	int yLocation;

	/** The items. */
	private List<Component> items = new ArrayList<Component>();

	/**
	 * Instantiates a new pop up button.
	 * 
	 * @param action
	 *            the action
	 * @param location
	 *            the location
	 */
	public PopUpButton(Action action, int location) {
		super();
		setAction(action);
		setPreferredSize(new Dimension(20, 20));
		setButton(location);
	}

	/**
	 * Instantiates a new pop up button.
	 * 
	 * @param image
	 *            the image
	 * @param location
	 *            the location
	 */
	public PopUpButton(ImageIcon image, int location) {
		super(null, image);
		setPreferredSize(new Dimension(20, 20));
		setButton(location);
	}

	/**
	 * Instantiates a new pop up button.
	 * 
	 * @param text
	 *            the text
	 * @param location
	 *            the location
	 */
	public PopUpButton(String text, int location) {
		super(text, null);
		//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getFontMetrics(FontSingleton.GENERAL_FONT).stringWidth(text) + 20, 20));
		setButton(location);
		GuiUtils.applyComponentOrientation(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component item) {
		if (!(item instanceof JSeparator)) {
			items.add(item);
		}
		Component c = menu.add(item);
		GuiUtils.applyComponentOrientation(menu);
		return c;
	}

	/**
	 * Gets the location property.
	 * 
	 * @return the location property
	 */
	int getLocationProperty() {
		return location;
	}

	/**
	 * Removes the all items.
	 */
	public void removeAllItems() {
		menu.removeAll();
		items.clear();
	}

	/**
	 * Sets the button.
	 * 
	 * @param location
	 *            the new button
	 */
	private void setButton(int location) {
		object = this;
		this.location = location;
		menu = new JPopupMenu();
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setMenuLocation(getLocationProperty());
				menu.show(object, xLocation, yLocation);
			}
		});
	}

	/**
	 * Sets the menu location.
	 * 
	 * @param location
	 *            the new menu location
	 */
	void setMenuLocation(int location) {
		if (!items.isEmpty()) {
			if (location == TOP_LEFT || location == TOP_RIGHT) {
				yLocation = -(int) items.get(0).getPreferredSize().getHeight() * items.size() - 5;
			} else {
				yLocation = 21;
			}
			if (location == TOP_LEFT || location == BOTTOM_LEFT) {
				int maxWidth = 0;
				for (int i = 0; i < items.size(); i++) {
					if ((int) items.get(i).getPreferredSize().getWidth() > maxWidth) {
						maxWidth = (int) items.get(i).getPreferredSize().getWidth();
					}
				}
				xLocation = -maxWidth + (int) getPreferredSize().getWidth();
			} else {
				xLocation = 0;
			}
		}
	}

}
