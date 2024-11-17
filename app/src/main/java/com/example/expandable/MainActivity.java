package com.example.expandable;



import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> groupData;
    HashMap<String, List<String>> childData;
    int lastExpandedGroup = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ExpandableListView
        expandableListView = findViewById(R.id.expandableListView);

        // Set up data
        prepareData();

        // Set adapter
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, groupData, childData);
        expandableListView.setAdapter(adapter);

        // Handle group click
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            String groupText = groupData.get(groupPosition);
            Toast.makeText(getApplicationContext(), groupText, Toast.LENGTH_SHORT).show();
            return false;
        });

        // Handle child click
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String childText = childData.get(groupData.get(groupPosition)).get(childPosition);
            Toast.makeText(getApplicationContext(), childText, Toast.LENGTH_SHORT).show();
            return false;
        });

        // Collapse other groups when a new group is expanded
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedGroup != -1 && lastExpandedGroup != groupPosition) {
                expandableListView.collapseGroup(lastExpandedGroup);
            }
            lastExpandedGroup = groupPosition;
        });
    }

    private void prepareData() {
        // Group headers
        String[] headers = {"Fruits", "Vehicles", "Countries"};
        // Child data for each group
        String[][] children = {
                {"Apple", "Banana", "Mango"},
                {"Car", "Bike", "Airplane"},
                {"USA", "India", "Japan"}
        };

        // Initialize lists
        groupData = new ArrayList<>();
        childData = new HashMap<>();

        // Populate data
        for (int i = 0; i < headers.length; i++) {
            groupData.add(headers[i]);
            List<String> childList = new ArrayList<>();
            for (String child : children[i]) {
                childList.add(child);
            }
            childData.put(groupData.get(i), childList);
        }
    }
}
