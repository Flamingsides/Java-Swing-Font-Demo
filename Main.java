import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Main
{
	private static final int F_WIDTH = 1000;
	private static final int F_HEIGHT = 500;

	private JFrame frame;
	private FontDemo demo;
	private JTextField textInput;
	private JSpinner fontSizeInput;
	
	public Main()
	{
		this.frame = new JFrame();
		this.frame.setTitle("Fonts Demo");
		this.frame.setSize(F_WIDTH, F_HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		
		this.demo = new FontDemo();
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();

		Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(padding);
		contentPanel.setLayout(new BorderLayout());
		main.frame.setContentPane(contentPanel);

		JLabel textInputLabel = new JLabel("Enter Text Here:");
		main.textInput = new JTextField();
		
		JPanel textInputPanel = new JPanel(new GridLayout(2, 1));		
		textInputPanel.add(textInputLabel);
		textInputPanel.add(main.textInput);
		
		String[] fontStyles = {"Plain", "Bold", "Italic", "Bold + Italic"};
		JComboBox<String> fontStyleChooser = new JComboBox<String>(fontStyles);
		
		SpinnerNumberModel model = new SpinnerNumberModel(15, 10, 20, 1);
		main.fontSizeInput = new JSpinner(model);

		JButton previewButton = new JButton("Preview");
		
		JPanel inputPanel = new JPanel(new GridLayout(1, 4, 20, 20));
		contentPanel.add(inputPanel, BorderLayout.NORTH);
		
		inputPanel.add(textInputPanel);
		inputPanel.add(fontStyleChooser);
		inputPanel.add(main.fontSizeInput);
		inputPanel.add(previewButton);
		
		JPanel demoPanel = new JPanel(new BorderLayout());
		demoPanel.add(new JPanel(), BorderLayout.NORTH);
		JPanel textPanel = new JPanel(new BorderLayout());
		demoPanel.add(textPanel, BorderLayout.CENTER);
		
		textPanel.setBackground(Color.WHITE);
		textPanel.add(main.demo, BorderLayout.CENTER);
		
		contentPanel.add(demoPanel);
		
		previewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me)
			{
				int fontStyleDecoded;
				String fontStyle = (String) fontStyleChooser.getSelectedItem();
				if (fontStyle.equals("Bold + Italic"))
				{
					fontStyleDecoded = Font.BOLD | Font.ITALIC;
				}
				else if (fontStyle.equals("Bold"))
				{
					fontStyleDecoded = Font.BOLD;
				}
				else if (fontStyle.equals("Italic"))
				{
					fontStyleDecoded = Font.ITALIC;
				}
				else
				{
					fontStyleDecoded = Font.PLAIN;
				}
				
				main.demo
				.setDemoText(main.textInput.getText())
				.setFontSize((int) main.fontSizeInput.getValue())
				.setFontStyle(fontStyleDecoded);
				
				main.demo.repaint();
				main.demo.setVisible(true);
				main.frame.repaint();
				main.frame.setVisible(true);
			}
		});
		
		main.frame.setVisible(true);
	}
}

class FontDemo extends JComponent
{

	private static final String[] FONTS = {"Arial", "SansSerif", "Serif", "Monospaced", "Dialog", "DialogInput"};
	
	private String demoText;
	private int fontSize;
	private int fontStyle;
	
	public FontDemo()
	{
		this.demoText = null;
		this.fontSize = 0;
		this.fontStyle = 0;
	}
	
	public FontDemo(String demoText, int fontSize, int fontStyle)
	{
		this.demoText = demoText;
		this.fontSize = fontSize;
		this.fontStyle = fontStyle;
	}
	
	public void paintComponent(Graphics g)
	{
		if (demoText != null)
		{
			Graphics2D g2 = (Graphics2D) g;
			
			int y = 50;
			g2.setFont(new Font(getFont().getName(), Font.PLAIN, 15));
			for (String s : FONTS)
			{
				g2.drawString(s + ": ", 50, y);
				y += 50;
			}
			
			g2.drawLine(180, 40, 180, 50 * FONTS.length + 10);
			
			y = 50;
			for (String s : FONTS)
			{
				Font font = new Font(s, this.fontStyle, this.fontSize);
				g2.setFont(font);
				g2.drawString(this.demoText, 200, y);
				y += 50;
			}
		}
	}
	
	public String getDemoText()
	{
		return demoText;
	}
	
	public FontDemo setDemoText(String demoText)
	{
		this.demoText = demoText;
		
		return this;
	}
	
	public int getFontSize()
	{
		return fontSize;
	}
	
	public FontDemo setFontSize(int fontSize)
	{
		this.fontSize = fontSize;
		
		return this;
	}
	
	public int getFontStyle()
	{
		return fontStyle;
	}
	
	public FontDemo setFontStyle(int fontStyle)
	{
		this.fontStyle = fontStyle;
		
		return this;
	}
}
