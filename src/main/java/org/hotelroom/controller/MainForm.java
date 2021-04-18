package org.hotelroom.controller;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumSet;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mindfusion.common.*;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.Item;
import com.mindfusion.scheduling.model.ItemEvent;
import com.mindfusion.scheduling.model.RecurrenceState;
import com.mindfusion.scheduling.standardforms.AppointmentForm;
import com.mindfusion.scheduling.standardforms.DialogResult;

public class MainForm extends JFrame
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    new MainForm().setVisible(true);
                }
                catch (Exception exp)
                {
                }
            }
        });
    }

    protected MainForm()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setTitle("Java Swing Scheduling Library: Appointment Forms");

        calendar = new Calendar();
        getContentPane().add(calendar, BorderLayout.CENTER);
        calendar.beginInit();
        calendar.setCurrentView(CalendarView.WeekRange);
        calendar.getWeekRangeSettings().setHeaderStyle(EnumSet.of(WeekRangeHeaderStyle.Title));
        calendar.setDate(new DateTime(2019, 12, 1));
        calendar.setEndDate(new DateTime(2020, 12, 1));
        calendar.setCurrentTime(new DateTime(2019, 12, 8));
        calendar.getWeekRangeSettings().setDayOfWeekFormat(DayOfWeekFormat.Full);
        calendar.setEnableDragCreate(true);


        calendar.endInit();

        calendar.addCalendarListener(new CalendarAdapter(){

            private void showForm(Item item)
            {
                AppointmentForm form = new AppointmentForm(calendar.getSchedule());
                form.setAppointment((Appointment)item);
                form.setVisible(true);

                form.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosed(WindowEvent we)
                    {
                        if (form.getDialogResult() == DialogResult.Remove)
                        {
                           
                            if (item.getRecurrenceState() == RecurrenceState.Occurrence ||
                                    item.getRecurrenceState() == RecurrenceState.Exception)
                                item.getRecurrence().markException(item, true);
                            else
                                calendar.getSchedule().getItems().remove(item);
                        }
                    }
                });


            }

            @Override
            public void itemClick(ItemMouseEvent e)
            {
                showForm(e.getItem());

            }

            @Override
            public void itemCreated(ItemEvent e) {

                calendar.getSelection().reset();
                calendar.getSelection().add(e.getItem().getStartTime(),
                        e.getItem().getEndTime());

                showForm(e.getItem());

            }
        });


    }

    private static final long serialVersionUID = 1L;
    private Calendar calendar;
}
