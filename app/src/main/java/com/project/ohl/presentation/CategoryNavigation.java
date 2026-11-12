package com.project.ohl.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.video_section.all_categories.AllCategoriesActivity;
import com.project.ohl.presentation.video_section.alphabet.AlphabetActivity;
import com.project.ohl.presentation.video_section.animals.AnimalsActivity;
import com.project.ohl.presentation.video_section.animals.household.HouseholdAnimalsActivity;
import com.project.ohl.presentation.video_section.animals.wild.WildAnimalsActivity;
import com.project.ohl.presentation.video_section.colors.ColorsActivity;
import com.project.ohl.presentation.video_section.emotions.EmotionsActivity;
import com.project.ohl.presentation.video_section.expressions.ExpressionsActivity;
import com.project.ohl.presentation.video_section.forms_of_address.FormsOfAddressActivity;
import com.project.ohl.presentation.video_section.numbers.NumbersActivity;
import com.project.ohl.presentation.video_section.objects.ObjectsActivity;
import com.project.ohl.presentation.video_section.objects.city.CityActivity;
import com.project.ohl.presentation.video_section.objects.classroom.ClassActivity;
import com.project.ohl.presentation.video_section.objects.home.HomeActivity;
import com.project.ohl.presentation.video_section.objects.outside.OutsideActivity;
import com.project.ohl.presentation.video_section.objects.shop.ShopActivity;
import com.project.ohl.presentation.video_section.objects.shop.money.MoneyActivity;
import com.project.ohl.presentation.video_section.objects.shop.products.ProductsActivity;
import com.project.ohl.presentation.video_section.people.PeopleActivity;
import com.project.ohl.presentation.video_section.people.family_members.FamilyMembersActivity;
import com.project.ohl.presentation.video_section.people.pronouns.PronounsActivity;
import com.project.ohl.presentation.video_section.verbs.VerbsActivity;
import java.util.Map;
import java.util.UUID;

import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_NAME;

public final class CategoryNavigation {
    public static final String PARENT_ID = "parentId";
    private static final Map<UUID, Class<? extends Activity>> SCREENS = Map.ofEntries(
            Map.entry(UUID.fromString("1deb5200-b06d-3977-9426-e2ea373195f9"), ExpressionsActivity.class),
            Map.entry(UUID.fromString("990e2591-117c-3b04-9146-dbdf0ca772b4"), AlphabetActivity.class),
            Map.entry(UUID.fromString("f4b19888-b0c4-3210-82d0-f7819ee177f1"), AnimalsActivity.class),
            Map.entry(UUID.fromString("9460c84d-d209-38a0-9cff-233fdbc0f93d"), CityActivity.class),
            Map.entry(UUID.fromString("39e0fa00-4a97-32d8-9ab3-bf8bdf96f51c"), ClassActivity.class),
            Map.entry(UUID.fromString("70e10e99-a6fa-3f8b-a049-8b9cf947da16"), ColorsActivity.class),
            Map.entry(UUID.fromString("05775e2e-42bf-3c5f-9eab-2bffdcd4b6e9"), EmotionsActivity.class),
            Map.entry(UUID.fromString("84bcd6f5-5eb8-3836-b1ea-d1d52df6d372"), FamilyMembersActivity.class),
            Map.entry(UUID.fromString("0d2aeac2-5c13-3be8-9d51-b1793c10bcb8"), FormsOfAddressActivity.class),
            Map.entry(UUID.fromString("bcda546e-6fd6-3539-bc01-5561dd0bb599"), HomeActivity.class),
            Map.entry(UUID.fromString("07fd780a-80ed-38c1-bdc5-5a66803a20f2"), HouseholdAnimalsActivity.class),
            Map.entry(UUID.fromString("d77ca8aa-905f-34e7-9052-a803058d3297"), MoneyActivity.class),
            Map.entry(UUID.fromString("93480539-6dcd-374f-92a8-e8197c2de039"), NumbersActivity.class),
            Map.entry(UUID.fromString("7833d27a-bde2-36eb-a15e-be9bcb34169a"), ObjectsActivity.class),
            Map.entry(UUID.fromString("81c916f9-d97c-3129-9d89-2fc799303f86"), OutsideActivity.class),
            Map.entry(UUID.fromString("fa877163-83e7-3788-ba1e-65d7b775a029"), PeopleActivity.class),
            Map.entry(UUID.fromString("811513e4-f046-3732-9429-c8fc4e06c19d"), ProductsActivity.class),
            Map.entry(UUID.fromString("8da2a487-ecd6-380a-a690-8c2492de89b8"), PronounsActivity.class),
            Map.entry(UUID.fromString("befda49a-46d5-307c-b33a-b014e7c4f438"), ShopActivity.class),
            Map.entry(UUID.fromString("2928cfce-6688-36df-91bd-78c777215785"), VerbsActivity.class),
            Map.entry(UUID.fromString("593d70bf-5431-3e72-9968-18092786dd81"), WildAnimalsActivity.class));

    private CategoryNavigation() {}

    public static Class<? extends Activity> screen(UUID categoryId) {
        return SCREENS.getOrDefault(categoryId, AllCategoriesActivity.class);
    }

    public static Intent intent(Context context, Category category) {
        return new Intent(context, screen(category.getId()))
                .putExtra(PARENT_ID, category.getId().toString())
                .putExtra(CATEGORY_NAME, category.getName())
                .putExtra(CATEGORY_IMAGE, category.getImageSource() == null ? 0 : category.getImageSource().intValue());
    }

    public static UUID parentId(Activity activity) {
        return requiredId(activity.getIntent(), PARENT_ID);
    }

    private static UUID requiredId(Intent intent, String key) {
        String value = intent.getStringExtra(key);
        if (value == null) throw new IllegalArgumentException("Missing " + key);
        return UUID.fromString(value);
    }
}
