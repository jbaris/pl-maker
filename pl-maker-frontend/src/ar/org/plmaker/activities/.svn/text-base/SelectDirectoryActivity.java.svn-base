package ar.org.plmaker.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Activity para escolha de arquivos/diretorios.
 * 
 * @author android
 * 
 */
public class SelectDirectoryActivity extends ListActivity {

	/**
	 * Chave de um item da lista de paths.
	 */
	private static final String ITEM_KEY = "key";
	public static final String TITLE_KEY = "title";

	/**
	 * Imagem de um item da lista de paths (diretorio ou arquivo).
	 */
	private static final String ITEM_IMAGE = "image";

	/**
	 * Diretorio raiz.
	 */
	private static final String ROOT = "/";

	/**
	 * Parametro de entrada da Activity: path inicial. Padrao: ROOT.
	 */
	public static final String START_PATH = "START_PATH";

	/**
	 * Parametro de saida da Activity: path escolhido. Padrao: null.
	 */
	public static final String RESULT_PATH = "RESULT_PATH";

	/**
	 * Parametro de entrada da Activity: tipo de selecao: pode criar novos paths
	 * ou nao. Padrao: nao permite.
	 * 
	 * @see {@link SelectionMode}
	 */
	public static final String SELECTION_MODE = "SELECTION_MODE";

	private List<String> path = null;
	private TextView myPath;
	private ArrayList<HashMap<String, Object>> mList;

	private Button selectButton;

	private LinearLayout layoutSelect;
	private InputMethodManager inputManager;
	private String parentPath;
	private String currentPath = ROOT;

	private File selectedFile;
	private final HashMap<String, Integer> lastPositions = new HashMap<String, Integer>();

	private void addItem(String fileName, int imageId) {
		final HashMap<String, Object> item = new HashMap<String, Object>();
		item.put(ITEM_KEY, fileName);
		item.put(ITEM_IMAGE, imageId);
		mList.add(item);
	}

	private void getDir(String dirPath) {

		final boolean useAutoSelection = dirPath.length() < currentPath
				.length();

		final Integer position = lastPositions.get(parentPath);

		getDirImpl(dirPath);

		if (position != null && useAutoSelection) {
			getListView().setSelection(position);
		}

	}

	/**
	 * Monta a estrutura de arquivos e diretorios filhos do diretorio fornecido.
	 * 
	 * @param dirPath
	 *            Diretorio pai.
	 */
	private void getDirImpl(final String dirPath) {

		currentPath = dirPath;

		final List<String> item = new ArrayList<String>();
		path = new ArrayList<String>();
		mList = new ArrayList<HashMap<String, Object>>();

		File f = new File(currentPath);
		File[] files = f.listFiles();
		if (files == null) {
			currentPath = ROOT;
			f = new File(currentPath);
			files = f.listFiles();
		}
		myPath.setText(getText(R.string.location) + ": " + currentPath);

		if (!currentPath.equals(ROOT)) {

			item.add(ROOT);
			addItem(ROOT, R.drawable.folder);
			path.add(ROOT);

			item.add("../");
			addItem("../", R.drawable.folder);
			path.add(f.getParent());
			parentPath = f.getParent();

		}

		final TreeMap<String, String> dirsMap = new TreeMap<String, String>();
		final TreeMap<String, String> dirsPathMap = new TreeMap<String, String>();
		final TreeMap<String, String> filesMap = new TreeMap<String, String>();
		final TreeMap<String, String> filesPathMap = new TreeMap<String, String>();
		for (final File file : files) {
			if (file.isDirectory()) {
				final String dirName = file.getName();
				dirsMap.put(dirName, dirName);
				dirsPathMap.put(dirName, file.getPath());
			}
		}
		item.addAll(dirsMap.tailMap("").values());
		item.addAll(filesMap.tailMap("").values());
		path.addAll(dirsPathMap.tailMap("").values());
		path.addAll(filesPathMap.tailMap("").values());

		final SimpleAdapter fileList = new SimpleAdapter(this, mList,
				R.layout.select_directory_row, new String[] { ITEM_KEY,
						ITEM_IMAGE }, new int[] { R.id.fdrowtext,
						R.id.fdrowimage });

		for (final String dir : dirsMap.tailMap("").values()) {
			addItem(dir, R.drawable.folder);
		}

		for (final String file : filesMap.tailMap("").values()) {
			addItem(file, R.drawable.file);
		}

		fileList.notifyDataSetChanged();

		setListAdapter(fileList);

	}

	/**
	 * Called when the activity is first created. Configura todos os parametros
	 * de entrada e das VIEWS..
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED, getIntent());

		final Bundle bundle = getIntent().getExtras();
		final String title = bundle.getString(TITLE_KEY);
		setTitle(title + " | " + getString(R.string.select_location));

		setContentView(R.layout.select_directory);
		myPath = (TextView) findViewById(R.id.path);

		inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		selectButton = (Button) findViewById(R.id.fdButtonSelect);
		selectButton.setEnabled(false);
		selectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selectedFile != null) {
					getIntent().putExtra(RESULT_PATH, selectedFile.getPath());
					setResult(RESULT_OK, getIntent());
					finish();
				}
			}
		});

		layoutSelect = (LinearLayout) findViewById(R.id.fdLinearLayoutSelect);

		String startPath = getIntent().getStringExtra(START_PATH);
		startPath = startPath != null ? startPath : ROOT;

		final File file = new File(startPath);
		selectedFile = file;
		selectButton.setEnabled(true);

		getDir(startPath);
	}

	/**
	 * Quando clica no item da lista, deve-se: 1) Se for diretorio, abre seus
	 * arquivos filhos; 2) Se puder escolher diretorio, define-o como sendo o
	 * path escolhido. 3) Se for arquivo, define-o como path escolhido. 4) Ativa
	 * botao de selecao.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		final File file = new File(path.get(position));

		setSelectVisible(v);

		if (file.isDirectory()) {
			selectButton.setEnabled(false);
			if (file.canRead()) {
				lastPositions.put(currentPath, position);
				getDir(path.get(position));
				selectedFile = file;
				v.setSelected(true);
				selectButton.setEnabled(true);

			} else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.icon)
						.setTitle(
								"[" + file.getName() + "] "
										+ getText(R.string.cant_read_folder))
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();
			}
		} else {
			selectedFile = file;
			v.setSelected(true);
			selectButton.setEnabled(true);
		}
	}

	/**
	 * Define se o botao de SELECT e visivel.
	 * 
	 * @param v
	 */
	private void setSelectVisible(View v) {
		layoutSelect.setVisibility(View.VISIBLE);

		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
		selectButton.setEnabled(false);
	}

}