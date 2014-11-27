/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.util;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.persist.PaginationSupport;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.ui.view.panel.IPage;

/**
 * 分页状态栏
 *@author DuanYong
 *@since 2012-12-8下午10:10:37
 *@version 1.0
 */
@Component("paginationBar")
@Scope("prototype")
public class PaginationBar extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	/**
	 * 每页显示多少条
	 */
	private Integer[] pageSizeArr = new Integer[]{20, 50, 100};
	private JButton previous;
	private JButton next;
	private JButton first;
	private JButton last;
	private JLabel statusLabel;
	private JComboBox pageSizeComb = null;
	private JTextField pageNoText;
	private JButton go;
    private final static int H_BUT_PAD = 5;
    private PaginationSupport paginationSupport;
    private IPage listPage;

    public PaginationBar(){
    	this(new PaginationSupport(new ArrayList(),0,0,20));
    }
    public PaginationBar(PaginationSupport paginationSupport) {
        super();
        this.paginationSupport = paginationSupport;
        this.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        this.setLayout(new BorderLayout());
        Box containerBox = Box.createVerticalBox();
        Box statusBox = Box.createHorizontalBox();

        // Left status area
        statusBox.add(Box.createRigidArea(new Dimension(10, 22)));
        previous = ButtonUtil.makeNavigationButton(null, "previous", LanguageLoader.getString("PaginationBar.previous"), LanguageLoader.getString("PaginationBar.previous"), this);
        next = ButtonUtil.makeNavigationButton(null, "next", LanguageLoader.getString("PaginationBar.next"), LanguageLoader.getString("PaginationBar.next"), this);
        first = ButtonUtil.makeNavigationButton(null, "first", LanguageLoader.getString("PaginationBar.first"), LanguageLoader.getString("PaginationBar.first"), this);
        last = ButtonUtil.makeNavigationButton(null, "last", LanguageLoader.getString("PaginationBar.last"), LanguageLoader.getString("PaginationBar.last"), this);

        pageSizeComb = new JComboBox(pageSizeArr) {

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(1000, getPreferredSize().height);
            }
        };

        pageSizeComb.setActionCommand("changePageSize");
        pageSizeComb.setSelectedItem(new Integer(paginationSupport.getPageSize()));
        pageSizeComb.addActionListener(this);

        pageNoText = new JTextField(10) {

            @Override
            protected Document createDefaultModel() {
                return new IntDocument();
            }

            class IntDocument extends PlainDocument {

                @Override
                public void insertString(int offs, String str, AttributeSet a)
                        throws BadLocationException {
                    if (str == null) {
                        return;
                    }
                    str = str.replaceAll("[^\\d]", "");
                    super.insertString(offs, str, a);
                }
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(1000, getPreferredSize().height);
            }
        };
        pageNoText.setActionCommand("go");
        pageNoText.addActionListener(this);

        go = ButtonUtil.makeNavigationButton(null, "go", "GO", "GO", this);



        statusLabel = new JLabel("<html>"+LanguageLoader.getString("PaginationBar.findStr") + paginationSupport.getTotalCount() + LanguageLoader.getString("PaginationBar.totalStr")+","+LanguageLoader.getString("PaginationBar.showStr") + paginationSupport.getCurrStartIndex() + LanguageLoader.getString("PaginationBar.toStr") + paginationSupport.getCurrPageSize() + " ,"+LanguageLoader.getString("PaginationBar.currAtStr")+"&nbsp;" + (paginationSupport.getCurrPageNumber()) + "&nbsp;"+LanguageLoader.getString("PaginationBar.pageStr")+" ,"+LanguageLoader.getString("PaginationBar.allStr")+"&nbsp;" + (paginationSupport.getPageTotalCount()) + "&nbsp;"+LanguageLoader.getString("PaginationBar.pageStr")+"</html>");

        Box pageSizePanel = Box.createHorizontalBox();
        pageSizePanel.add(new JLabel(LanguageLoader.getString("PaginationBar.pageShowStr")));
        pageSizePanel.add(pageSizeComb);
        pageSizePanel.add(new JLabel(LanguageLoader.getString("PaginationBar.totalStr")));

        Box goPanel = Box.createHorizontalBox();
        goPanel.add(new JLabel(LanguageLoader.getString("PaginationBar.gotoStr")));
        goPanel.add(pageNoText);
        goPanel.add(new JLabel(LanguageLoader.getString("PaginationBar.pageStr")));
        goPanel.add(go);

        //statusBox.add(statusLabel);
        statusBox.add(Box.createHorizontalGlue());
        statusBox.add(first);
        statusBox.add(Box.createHorizontalStrut(H_BUT_PAD));
        statusBox.add(previous);
        statusBox.add(Box.createHorizontalStrut(H_BUT_PAD));
        statusBox.add(next);
        statusBox.add(Box.createHorizontalStrut(H_BUT_PAD));
        statusBox.add(last);
        statusBox.add(Box.createHorizontalStrut(H_BUT_PAD));
        statusBox.add(pageSizePanel);
        statusBox.add(goPanel);
        containerBox.add(Box.createHorizontalGlue());
        containerBox.add(statusLabel);
        containerBox.add(statusBox);



        //</snip>
        this.add(containerBox);

        initButtonStatus();

    }
    /*
     * 设置按钮状态
     */

    private void initButtonStatus() {
        first.setEnabled(!paginationSupport.isFirstPage());
        previous.setEnabled(paginationSupport.hasPreviousPage());
        next.setEnabled(paginationSupport.hasNextPage());
        last.setEnabled(!paginationSupport.isLastPage());
    }

    final public void loadData() {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                loading();
            }
        });
    }
    final private void loading() {
        query();
    }

    final private void query() {
        finished();
    }
    /*
     * 查询完成
     */

    final private void finished() {
        initButtonStatus();
        statusLabel.setText("<html>"+LanguageLoader.getString("PaginationBar.findStr") + paginationSupport.getTotalCount() + LanguageLoader.getString("PaginationBar.totalStr")+","+LanguageLoader.getString("PaginationBar.showStr") + paginationSupport.getCurrStartIndex() + LanguageLoader.getString("PaginationBar.toStr") + paginationSupport.getCurrPageSize() + " ,"+LanguageLoader.getString("PaginationBar.currAtStr")+"&nbsp;" + (paginationSupport.getCurrPageNumber()) + "&nbsp;"+LanguageLoader.getString("PaginationBar.pageStr")+" ,"+LanguageLoader.getString("PaginationBar.allStr")+"&nbsp;" + (paginationSupport.getPageTotalCount()) + "&nbsp;"+LanguageLoader.getString("PaginationBar.pageStr")+"</html>");
    }

    public void setPaginationSupport(PaginationSupport paginationSupport) {
        this.paginationSupport = paginationSupport;
    }

    public PaginationSupport getPaginationSupport() {
        return paginationSupport;
    }

     
    public IPage getListPage() {
		return listPage;
	}
	public void setListPage(IPage listPage) {
		this.listPage = listPage;
	}
	public void actionPerformed(ActionEvent ae) {
    	 
        String command = ae.getActionCommand();
        if (command.equals("next")) {
        	System.out.println("=====paginationSupport.getNextStartIndex():"+paginationSupport.getNextStartIndex());
        	listPage.showData(paginationSupport.getNextStartIndex(), paginationSupport.getPageSize());
        } else if (command.equals("previous")) {
        	System.out.println("=====paginationSupport.getPrevStartIndex():"+paginationSupport.getPrevStartIndex());
        	listPage.showData(paginationSupport.getPrevStartIndex(), paginationSupport.getPageSize());
        } else if (command.equals("first")) {
        	listPage.showData(paginationSupport.getFirstStartIndex(), paginationSupport.getPageSize());
        } else if (command.equals("last")) {
        	System.out.println("=====paginationSupport.getLastStartIndex():"+paginationSupport.getLastStartIndex());
        	listPage.showData(paginationSupport.getLastStartIndex(), paginationSupport.getPageSize());
        } else if (command.equals("go")) {
            if (this.pageNoText.getText() != null && !"".equals(this.pageNoText.getText().trim())) {
            	int startIndex = Integer.parseInt(this.pageNoText.getText().trim());
            	if(startIndex > paginationSupport.getPageTotalCount()){
            		startIndex = paginationSupport.getPageTotalCount();
            	}
        		startIndex = startIndex - 1;
            	if(startIndex < 0){
            		startIndex = 0;
            	}
            	startIndex = startIndex * paginationSupport.getPageSize();
            	listPage.showData(startIndex, paginationSupport.getPageSize());
                this.pageNoText.setText("");
            }

        } else if (command.equals("changePageSize")) {
        	listPage.showData(0, Integer.parseInt(this.pageSizeComb.getSelectedItem().toString()));
        }

    }
}
