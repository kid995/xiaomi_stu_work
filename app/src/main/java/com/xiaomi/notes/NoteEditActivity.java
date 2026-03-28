package com.xiaomi.notes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NoteEditActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editContent;
    private NoteViewModel noteViewModel;
    private Note existingNote;
    private boolean isNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editTitle = findViewById(R.id.edit_note_title);
        editContent = findViewById(R.id.edit_note_content);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        int noteId = getIntent().getIntExtra(MainActivity.EXTRA_NOTE_ID, -1);
        if (noteId != -1) {
            isNewNote = false;
            setTitle(R.string.edit_note);
            new Thread(() -> {
                existingNote = noteViewModel.getNoteById(noteId);
                if (existingNote != null) {
                    runOnUiThread(() -> {
                        editTitle.setText(existingNote.getTitle());
                        editContent.setText(existingNote.getContent());
                    });
                }
            }).start();
        } else {
            isNewNote = true;
            setTitle(R.string.new_note);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                saveAndExit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        if (deleteItem != null) {
            deleteItem.setVisible(!isNewNote);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            saveAndExit();
            return true;
        } else if (id == R.id.action_save) {
            saveAndExit();
            return true;
        } else if (id == R.id.action_delete) {
            showDeleteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            if (!isNewNote && existingNote != null) {
                noteViewModel.delete(existingNote);
            }
            finish();
            return;
        }

        long now = System.currentTimeMillis();
        if (isNewNote) {
            Note note = new Note(title, content, now, now);
            noteViewModel.insert(note);
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        } else if (existingNote != null) {
            existingNote.setTitle(title);
            existingNote.setContent(content);
            existingNote.setUpdatedAt(now);
            noteViewModel.update(existingNote);
            Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_note)
                .setMessage(R.string.delete_confirm)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    if (existingNote != null) {
                        noteViewModel.delete(existingNote);
                    }
                    finish();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
