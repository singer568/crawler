package org.javacoo.cowswing.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.ui.style.GuiUtils;


@org.springframework.stereotype.Component("pageContainer")
public class PageContainer extends JPanel implements AncestorListener {

	/** serialVersionUID. */
	private static final long serialVersionUID = -3878434827364565178L;

	static final int PREFERED_HEIGHT = 24;// 22

	/** the titleControlPanel. */
	private JPanel titleControlPanel;

	/** the pageContainer. */
	private JPanel pageContainer;

	/** the titleLabel. */
	private JLabel titleLabel;

	/** control toolBar */
	private JToolBar controlToolBar;

	/** the previousPageButton. */
	private JButton previousPageButton;

	/** the nextPageButton. */
	private JButton nextPageButton;

	/** the closePageButton. */
	private JButton closePageButton;

	/** the CardLayout for centerPane. */
	private CardLayout card;

	/** the CardLayout for currentPage. */
	private IPage currentPage;

	/** the CardLayout for centerPane. */
	private int currentPageIndex = 0;

	/** the default panel. */
	private IPage defaultPage;

	private PageContainer() {
		super();

		// set Layout.
		this.setLayout(new BorderLayout());

		// add the titleControlPanel.
		this.add(getTitleControlPanel(), BorderLayout.NORTH);

		// add the CardCotentPanel.
		this.add(getPageContainer(), BorderLayout.CENTER);
	}


	/**
	 * create titleControlPanel.
	 * 
	 * @return titleControlPanel
	 * */
	public JPanel getTitleControlPanel2() {
		if (titleControlPanel == null) {
			titleControlPanel = new JPanel();
			titleControlPanel.setPreferredSize(new Dimension(0, 28));

			GroupLayout layout = new GroupLayout(titleControlPanel);
			titleControlPanel.setLayout(layout);
			// layout.setAutoCreateGaps(true);
			// layout.setAutoCreateContainerGaps(true);

			layout
					.setHorizontalGroup(layout
							.createParallelGroup(
									javax.swing.GroupLayout.Alignment.CENTER)
							.addGroup(
									layout
											.createSequentialGroup()
											.addContainerGap()
											.addComponent(getTitleLabel())
											.addPreferredGap(
													javax.swing.LayoutStyle.ComponentPlacement.RELATED,
													156, Short.MAX_VALUE)
											.addComponent(
													getControlToolBar(),
													javax.swing.GroupLayout.PREFERRED_SIZE,
													javax.swing.GroupLayout.DEFAULT_SIZE,
													javax.swing.GroupLayout.PREFERRED_SIZE)));
			layout.setVerticalGroup(layout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup().addGroup(
							layout.createParallelGroup(
									GroupLayout.Alignment.LEADING, false)
									.addComponent(getTitleLabel())
									.addComponent(getControlToolBar(),
											GroupLayout.PREFERRED_SIZE, 25,
											GroupLayout.PREFERRED_SIZE))
							.addContainerGap()));
			titleControlPanel.setBorder(BorderFactory.createLineBorder(GuiUtils
					.getLookAndFeelColor("lineColor")));
			// getControlToolBar().setBorder(BorderFactory.createLineBorder(GuiUtils.getLookAndFeelColor("lineColor")));
		}
		return titleControlPanel;
	}

	/**
	 * create titleControlPanel.
	 * 
	 * @return titleControlPanel
	 * */
	public JPanel getTitleControlPanel() {
		if (titleControlPanel == null) {
			titleControlPanel = new JPanel();
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			titleControlPanel.setLayout(gridbag);
			// centerPane.setBorder(BorderFactory.createLineBorder(Color.red));

			int top = 2;
			int left = 1;
			int bottom = 2;
			int right = 1;
			Insets inserts = new Insets(top, left, bottom, right);
			/** type. */
			// getTitleLabel()
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 0.0;
			c.weighty = 0.0;
			c.insets = inserts;
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 1;
			titleControlPanel.add(getTitleLabel(), c);

			// spaceLabel
			c.gridx = GridBagConstraints.RELATIVE;
			c.weightx = 1.0;
			titleControlPanel.add(new JLabel(), c);
			c.weightx = 0.0;

			titleControlPanel.add(getPreviousPageButton(), c);
			titleControlPanel.add(getNextPageButton(), c);
			titleControlPanel.add(getClosePageButton(), c);

		}
		return titleControlPanel;
	}

	/**
	 * create centerPane.
	 * 
	 * @return centerPane
	 * */
	public JPanel getPageContainer() {
		if (pageContainer == null) {
			pageContainer = new JPanel();

			if (card == null) {
				card = new CardLayout();
				pageContainer.setLayout(card);
			}
		}

		return pageContainer;
	}

	/**
	 * @return titleLabel
	 * */
	private JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel();
			// test
			setTitleLabelText("Card Title");
			titleLabel.setBackground(Color.RED);
		}

		return titleLabel;
	}

	/**
	 * set the title Label text.
	 * */
	public void setTitleLabelText(String title) {
		if (titleLabel != null && title != null) {
			titleLabel.setText(title);
			// titleLabel.validate();
		}
	}

	private JToolBar getControlToolBar() {
		if (controlToolBar == null) {
			controlToolBar = new JToolBar();
			controlToolBar.setFloatable(false);
			controlToolBar.add(getPreviousPageButton());
			controlToolBar.add(getNextPageButton());
			controlToolBar.add(getClosePageButton());
		}
		return controlToolBar;
	}

	/** show previous card Label. */
	private JButton getPreviousPageButton() {
		if (previousPageButton == null) {
			previousPageButton = new JButton();
			previousPageButton.setEnabled(false);
			previousPageButton.setPreferredSize(new Dimension(24, 24));
			previousPageButton.setIcon(ImageLoader.getImageIcon("CrawlerResource.actionBack"));

			previousPageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					showPrevious();
				}
			});
		}
		return previousPageButton;
	}

	/** close card Button. */
	private JButton getNextPageButton() {
		if (nextPageButton == null) {
			nextPageButton = new JButton();
			nextPageButton.setPreferredSize(new Dimension(24, 24));
			nextPageButton.setIcon(ImageLoader.getImageIcon("CrawlerResource.actionForward"));
			nextPageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					showNext();
				}
			});
		}
		return nextPageButton;
	}

	/** close card Button. */
	private JButton getClosePageButton() {
		if (closePageButton == null) {
			closePageButton = new JButton();
			closePageButton.setPreferredSize(new Dimension(24, 24));
			closePageButton.setIcon(ImageLoader.getImageIcon("CrawlerResource.actionStop"));

			closePageButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (currentPage != null) {
						removeCardPanel();
					}
				}
			});
		}
		return closePageButton;
	}

	/**
	 * show the first Container.
	 * */
	public void showFirst() {
		card.first(pageContainer);
		// update
		updateTitleControlPanel();
	}

	/**
	 * show the next Container.
	 * */
	public void showNext() {
		card.next(pageContainer);
		// update
		updateTitleControlPanel();
	}

	/**
	 * show the previous Container.
	 * */
	public void showPrevious() {
		card.previous(pageContainer);
		// update
		updateTitleControlPanel();
	}

	/**
	 * show the last Container.
	 * */
	public void showLast() {
		card.last(pageContainer);
		// update
		updateTitleControlPanel();
	}

	public void show(String name) {
		card.show(pageContainer, name);
		// update
		updateTitleControlPanel();
	}

	public void show(Component comp, String name) {
	}

	public void isExist(Component comp) {
		// pageContainer.
	}

	private boolean isFirst() {
		return currentPageIndex == 0;
	}

	private boolean isLast() {
		return currentPageIndex == getPageCount() - 1;
	}

	private boolean isEmpty() {
		return getPageCount() == 0;
	}

	/**
	 * Adds the specified Component to this card layout's internal table of
	 * names.
	 * 
	 * @see java.awt.CardLayout#addLayoutComponent(java.awt.Component,
	 *      java.lang.Object)
	 * */
	public void addPage(Component comp, Object constraints) {
		getPageContainer().add(comp, constraints);
		show((String) constraints);
		// updateTitleControlPanel();
		// card.
		// updateTitleControlPanel();
	}

	/**
	 * Removes the specified Component from the layout.
	 * 
	 * @see java.awt.CardLayout#removeLayoutComponent(java.awt.Component)
	 * */
	public void removeCardPanel() {
		pageContainer.remove((Component) currentPage);
		card.removeLayoutComponent((Component) currentPage);

		// Dispose this card.
		currentPage.disposePage();
		currentPage = null;

		// update
		updateTitleControlPanel();
	}

	/**
	 * @return an Array of all cardPanel in the pageContainer.
	 * */
	public Component[] getPages() {
		return pageContainer.getComponents();
	}

	/**
	 * @return the number of cardPanel in the pageContainer.
	 * */
	public int getPageCount() {
		synchronized (pageContainer.getTreeLock()) {
			return pageContainer.getComponentCount();
		}
	}

	/**
	 * @return the visible card and update the current Card index.
	 * */
	public IPage findCurrentPage() {
		Component visibleComp = null;

		synchronized (pageContainer.getTreeLock()) {
			int ncomponents = pageContainer.getComponentCount();
			for (int i = 0; i < ncomponents; i++) {
				Component comp = pageContainer.getComponent(i);
				if (comp.isVisible()) {
					visibleComp = comp;
					currentPageIndex = i;
					break;
				}
			}
		}
		currentPage = (IPage) visibleComp;
		return (IPage) visibleComp;
	}

	private void updateTitleControlPanel() {
		// update the current card index.
		findCurrentPage();
  
		// set the control panel title.
		if (currentPage != null) {
			setTitleLabelText(currentPage.getPageName());
		} else {
			setTitleLabelText("Chang to Defautl title.");
			//currentPage is null return.
			return;
		}

		// if current card is the first card.
		if (isEmpty() || isFirst()) {
			previousPageButton.setEnabled(false);
		} else {
			previousPageButton.setEnabled(true);
		}

		// if current card is the last card.
		if (isEmpty() || isLast()) {
			nextPageButton.setEnabled(false);
		} else {
			nextPageButton.setEnabled(true);
		}

		if (currentPage.isDefaultPage()) {
			closePageButton.setEnabled(false);
		} else {
			closePageButton.setEnabled(true);
		}
	}

	public void setDefaultPage(IPage cardComponet) {
		if (cardComponet != null) {
			if (cardComponet instanceof Component) {
				defaultPage = cardComponet;
				defaultPage.setDefaultPage(true);
				addPage((Component) defaultPage, defaultPage
						.getPageId());
			}
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		// findCurrentCard();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}
}
