package com.timekeep.front.dialog;

import com.timekeep.back.JobsiteService;
import com.timekeep.data.Jobsite;
import com.timekeep.front.ItemListPresenter;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JobsiteModalPresenter {
  public final JDialog view;

  private JobsiteModalPresenter(JDialog view) {
    this.view = view;
  }

  public static JobsiteModalPresenter build(JFrame parent) {
    JDialog view = new JDialog(parent);
    view.setModal(true);
    view.setTitle("Jobsites");

    ItemListPresenter<Jobsite> jobsiteItemListPresenter = jobsiteList();

    JPanel panel = FillComponent.verticalFillBuilder().
        addCalculatedComponent(jobsiteItemListPresenter.view).
        addGivenComponent(jobsiteCreateForm(jobsiteItemListPresenter)).build();

    view.add(panel);
    view.setSize(200, 400);

    int parentWidth = parent.getWidth();
    int parentHeight = parent.getHeight();

    view.setLocation(parentWidth / 2 - 100, parentHeight / 2 - 200);

    return new JobsiteModalPresenter(view);
  }

  private static JPanel jobsiteCreateForm(final ItemListPresenter<Jobsite> jobsiteLists) {
    final JTextField nameField = new JTextField();
    final JTextField stateField = new JTextField();

    JButton createButton = new JButton("Create");

    createButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String state = stateField.getText();

        Jobsite jobsite = new Jobsite(name, state);
        JobsiteService.storeJobsite(jobsite);

        jobsiteLists.setItems(JobsiteService.getJobsites());
      }
    });

    return FillComponent.formBuilder(createButton).
        addInput("Name", nameField).
        addInput("State", stateField).build();
  }

  private static ItemListPresenter jobsiteList() {
    List<Jobsite> jobsiteList = JobsiteService.getJobsites();

    ItemListPresenter itemListPresenter =
        ItemListPresenter.build(new ItemListPresenter.ItemSelectionHandler<Jobsite>() {
          @Override
          public void selectItem(Jobsite item) {
          }
        });

    itemListPresenter.setItems(jobsiteList);

    return itemListPresenter;

    /* DefaultListModel<String> displayList = new DefaultListModel<String>();
    for(Jobsite jobsite : jobsiteList) {
      displayList.addElement(jobsite.name);
    }

    JList list = new JList(displayList);
    JScrollPane pane = new JScrollPane(list);

    return pane;*/
  }
}
