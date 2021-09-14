package ru.zotov.carracing.common.constant;

/**
 * @author Created by ZotovES on 17.08.2021
 * Константы
 */
public interface Constants {
    String KAFKA_GROUP_ID = "car_racing_group_id";
    String KAFKA_RACE_START_TOPIC = "race_start";
    String KAFKA_RACE_FINISH_TOPIC = "race_finish";
    String KAFKA_TO_REWARD_TOPIC = "reward";
    String KAFKA_RETURN_REWARD_TOPIC = "reward_return";
    String KAFKA_RACE_CANCEL_TOPIC = "race_cancel";
    String EXPAND_FUEL_SUCCESS_KAFKA_TOPIC = "fuel_success";
    String EXPAND_FUEL_FAIL_KAFKA_TOPIC = "fuel_fail";
    String KAFKA_FAIL_CHEAT_REVIEW_TOPIC = "fail_cheat_review";
    String KAFKA_PLAYER_TOPIC = "player";
    String KAFKA_CREATE_PROFILE_TOPIC = "create_profile";
    String KAFKA_SEND_MAIL_TOPIC = "send_mail";
    String PURCHASE_TRANSACTION_VALIDATE = "purchase_transaction_validate";
    String PURCHASE_FUEL_ADD = "purchase_fuel_add";
    String PROFILE_PROGRESS = "profile_progress";
    String PROFILE_REGRESS = "profile_regress";
}
