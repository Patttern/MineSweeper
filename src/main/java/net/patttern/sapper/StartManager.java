package net.patttern.sapper; /**
 * Invoker.
 * 
 * @author Pestov Oleg
 * @version 1.0
 */

import net.patttern.sapper.gui.MainFrame;

public class StartManager
{

    public static void main(String[] args)
    {
        MainFrame f = new MainFrame();
        
        f.setLocation(150, 150);
        f.setVisible(true);
    }
}