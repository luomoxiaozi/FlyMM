package com.meizu.smartbar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;

public class SmartBarUtils {

    /**
     * ���� ActionBar.setTabsShowAtBottom(boolean) ������
     * 
     * <p>��� android:uiOptions="splitActionBarWhenNarrow"���������ActionBar Tabs��ʾ�ڵ�����
     * 
     * <p>ʾ����</p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity implements ActionBar.TabListener {
     * 
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *         
     *         final ActionBar bar = getActionBar();
     *         bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
     *         SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);
     *         
     *         bar.addTab(bar.newTab().setText("tab1").setTabListener(this));
     *         ...
     *     }
     * }
     * </pre>
     */
    public static void setActionBarTabsShowAtBottom(ActionBar actionbar, boolean showAtBottom) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setTabsShowAtBottom", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, showAtBottom);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ���� ActionBar.setActionBarViewCollapsable(boolean) ������
     * 
     * <p>����ActionBar��������ʾ����ʱ�Ƿ����ء�
     * 
     * <p>ʾ����</p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     * 
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *         
     *         final ActionBar bar = getActionBar();
     *         
     *         // ����setActionBarViewCollapsable��������ActionBarû����ʾ���ݣ���ActionBar��������ʾ
     *         SmartBarUtils.setActionBarViewCollapsable(bar, true);
     *         bar.setDisplayOptions(0);
     *     }
     * }
     * </pre>
     */
    public static void setActionBarViewCollapsable(ActionBar actionbar, boolean collapsable) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionBarViewCollapsable", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, collapsable);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ���� ActionBar.setActionModeHeaderHidden(boolean) ������
     * 
     * <p>����ActionMode�����Ƿ����ء�
     * 
     * <p>ʾ����</p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     * 
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *         
     *         final ActionBar bar = getActionBar();
     *         
     *         // ActionBarתΪActionModeʱ������ʾActionMode����
     *         SmartBarUtils.setActionModeHeaderHidden(bar, true);
     *     }
     * }
     * </pre>
     */
    public static void setActionModeHeaderHidden(ActionBar actionbar, boolean hidden) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionModeHeaderHidden", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, hidden);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * ����ActionBar.setBackButtonDrawable(Drawable)����
     * 
     * <p>���÷��ؼ�ͼ��
     * 
     * <p>ʾ����</p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     * 
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *         
     *         final ActionBar bar = getActionBar();
     *         // �Զ���ActionBar�ķ��ؼ�ͼ��
     *         SmartBarUtils.setBackIcon(bar, getResources().getDrawable(R.drawable.ic_back));
     *         ...
     *     }
     * }
     * </pre>
     */
    public static void setBackIcon(ActionBar actionbar, Drawable backIcon) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setBackButtonDrawable", new Class[] { Drawable.class });
            try {
                method.invoke(actionbar, backIcon); 
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
