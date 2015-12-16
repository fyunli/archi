package uk.ivanc.archimvvm.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import uk.ivanc.archimvvm.R;
import uk.ivanc.archimvvm.RepositoryAdapter;
import uk.ivanc.archimvvm.databinding.MainActivityBinding;
import uk.ivanc.archimvvm.model.Repository;
import uk.ivanc.archimvvm.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        mainViewModel = new MainViewModel(this);
        binding.setViewModel(mainViewModel);

        setSupportActionBar(binding.toolbar);
        setupRecyclerView(binding.reposRecyclerView);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    // loadGithubRepos onComplete
    public void onEvent(List<Repository> repositories) {
        RepositoryAdapter adapter = (RepositoryAdapter) binding.reposRecyclerView.getAdapter();
        adapter.setRepositories(repositories);
        adapter.notifyDataSetChanged();
        hideSoftKeyboard();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        RepositoryAdapter adapter = new RepositoryAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextUsername.getWindowToken(), 0);
    }

    @OnClick(R.id.button_search)
    public void onButtonSearchClick(View v) {
        String username = binding.editTextUsername.getText().toString();
        mainViewModel.loadGithubRepos(username);
    }

    @OnTextChanged(R.id.edit_text_username)
    public void onEditTextUsernameChanged(CharSequence charSequence, int start, int before, int count) {
        mainViewModel.searchButtonVisibility.set(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
    }

}
