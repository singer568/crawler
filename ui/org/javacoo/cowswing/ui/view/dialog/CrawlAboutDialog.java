package org.javacoo.cowswing.ui.view.dialog;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingListener;

/**
 * 关于窗口
 * 
 * @author javacoo
 * @since 2012-03-14
 */
public class CrawlAboutDialog extends JDialog implements CowSwingListener{
	private static final long serialVersionUID = 1L;

	public CrawlAboutDialog(JFrame owner){
		super(owner,"淡泊以明志,宁静以致远",true);
		setBounds(new Rectangle(
						(int) owner.getX()+200,
						(int) owner.getY()+300, 
						300, 
						150
				));
		add(new JLabel("<html>如石子一粒,仰高山之巍峨,但不自惭形秽.<br /><br />若小草一棵,慕白杨之伟岸,却不妄自菲薄.</html>"),BorderLayout.CENTER);
		JButton ok = new JButton("确定");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		JPanel panel = new JPanel();
		panel.add(ok);
		add(panel,BorderLayout.SOUTH);
	}

	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}
