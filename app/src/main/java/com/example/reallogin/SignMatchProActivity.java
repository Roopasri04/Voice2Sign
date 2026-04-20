package com.example.reallogin;


import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SignMatchProActivity extends AppCompatActivity {

    RecyclerView optionsRecycler;
    TextView questionText, scoreText;

    List<GifModel> allSigns = new ArrayList<>();
    List<GifModel> currentOptions = new ArrayList<>();

    GifAdapter adapter;

    GifModel correctAnswer;
    int score = 0;

    Random random = new Random();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_match_pro);

        optionsRecycler = findViewById(R.id.optionsRecycler);
        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);

        loadAllSigns(); // same words as learning

        optionsRecycler.setLayoutManager(new GridLayoutManager(this, 3));

        loadQuestion();
    }

    private void loadAllSigns() {

        String[] wordNames = {
                "hello","thankyou","happy","sorry","please","ilearnsign","me","my name","nicetomeetyou","no","okay","please","sorry","tryagain","wonderful","yes","you","elephant","elevated","ice_hockey_player","slow_on_the_uptake","take_away","duck","give_rise_to","energetic","only_child","deposit","i_dont_want_transparency","to_switch_on","move_in_apartment","whom","to_include","railway_street","to_shop","united","to_build","two_hours","private_way","this_week","to_break_down","eye","likewise","electronic","student","thirst","announcement","to_surf","to_press","pressure","single","gorgeous","dept_restructuring","third","poetic_language","double_room","the_sound_of_thunder","angle_braces","locksmith","chain","ecuador_country","piece","corner","daily_routine","disco_dance","endowment","pedestrian","cola","comic","benefit_in_kind","diabetes","lawyer","back_then","to_nourish","therefore","computer_data","duration","tv","incorrect","dessert","obvious","journalistic","brain_skull","thief","this_time","ski_hooks","discussion","stroke_related","academic","guest_conductor","fate","half","small_piece_of_paper","freedom_of_expression","label_printer","imaginable","casting","additionally","translator","how_much","old_soup","quarter_past","flirt","networking","climate_change","religion","behave_wrongly","one_time","to_agree","to_learn","to_address_someone","thank_god","to_have_same_thoughts","celery","cauliflower","fire_fighters","to_be_very_hungry","until_early_in_the_morning","cherry_tree","to_be_envious","interior_minister","emergency_call","every_morning","central_area","to_confess","wooden_floor","factual_mistake","i_like_you","to_register","judo_practitioner","garlic","kiwi","clementine","decoration_wooden","artificial","is_not_able_to","kindergarten_teacher","flower_moon","does_not_fit","health_prevention","head","heart_break","income_tax","does_not_like","polite","specific","envious","load_conditions","formal_assembly","honorary_general_consulate","time_nine_and_half","cream","in_favour_of_it","against","towards_there","elegant","i_dont_know","surprising_event","insignificant","end","unsuccessful","essential","to_make_happy","to_fulfill_a_wish","adding_multiple_times","constitutional_law","additions","wedding_vow","i_have_trust_in_you","receiving","inheritance","strawberries","recorded_data","alimony","desperate_situation","to_raise","quarter_to","to_confuse","forgiveness","data_collected","either_or","hereditary","to_decide","resolution","to_comply_with","movie_subtitles","to_get_information","success","to_remind","to_refund","duty","anatomical","cent_sign","to_seed","federal_council","to_break","working","basketball_player","homeless","soup","carrot","for_my_sake","steep","to_bite","family_announcement","substance","themselves","to_enter","indoor_handball","for_you","sauces","footnote","unordered","sunny","special_school","special_offers","button","dumpling","doorbell","complex","savings_amount","small_gift","to_comment","to_stick","knee_sock","client","as_soon_as","padding","snow_boarding","bar","to_be_called","goal","chest_pass","to_fire","package_insert","flea","tick","cells","to_grind","strainer","yeast","gear","to_pour","wheel","bag","football","eager","employer","location","past","shield","traingle","middle","subway","to_list","to_understand","to_promote","to_multiply","to_protect","decrease","all_around","species","slider","to_gift","arrow_keys","need_not","groups","emission","eliminated","to_putdown","to_operate","alcohol","odor","active_substance","everything","itch","section","tub","arrogant","consistently","tune","impact","promotion","perspective","to_join_in","participation","mixing","whole_grain","pouring","microwave","package","carbohydrate","accumulation","blood_vessel","disease_of_the_arteries","obesity","blood_cell","blood_test","pilgrim","to_influence","dialysis","frighten","difficult","barrier","tour","info","catering","guidance","music","until","silent","otherwise","warn","apply","tingling","circulation","foot","hands","arms","stack_height","filter","connection","long","chips","digitalizing","how","diet","says","to_insert","perforated","bubbles","supporting","format","workcamp","across_the_world","to_check","square","to_smell","to_stream","school_lane","intention","pants","to_hold_on","to_pluck","branch","juicy","apple_tree","reception_area","guest_room","kids_room "

        // 🔥 ADD SAME ARRAY FROM LEARNING MODE HERE
        };

        for (String word : wordNames) {
            int resId = getResources().getIdentifier(word, "drawable", getPackageName());
            if (resId != 0) {
                allSigns.add(new GifModel(word, resId));
            }
        }
    }

    private void loadQuestion() {

        Collections.shuffle(allSigns);

        correctAnswer = allSigns.get(0);

        currentOptions.clear();
        currentOptions.add(correctAnswer);
        currentOptions.add(allSigns.get(1));
        currentOptions.add(allSigns.get(2));

        Collections.shuffle(currentOptions);

        questionText.setText("Select sign for: " +
                correctAnswer.getName().replace("_", " ").toUpperCase());

        adapter = new GifAdapter(this, currentOptions);
        optionsRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(selected -> {

            if (selected.getName().equals(correctAnswer.getName())) {
                score++;
                scoreText.setText("Score: " + score);
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

            handler.postDelayed(this::loadQuestion, 1000);
        });
    }
}