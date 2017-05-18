package xiaochen.you.meituan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dgaz on 2017/4/24.
 */

public class TestFragment extends Fragment {

    public static TestFragment newInstance(int tag) {
        TestFragment f = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        f.setArguments(bundle);
        return f;
    }

    private int tag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getInt("tag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (tag == 0) {
            return inflater.inflate(R.layout.fragment_test1, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_test2, container, false);
        }
    }


}
