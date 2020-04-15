package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.data.room_data.CurrentUserProfile
import com.demo.app.ncov2020.data.room_data.GameState
import com.demo.app.ncov2020.data.room_data.UserProfile

class GameRepositoryFacade private constructor() : BaseRepository() {
    private val userProfileRepo: UserRepo = UserRepo.INSTANCE
    private val currentUserRepo: CurrentUserRepo = CurrentUserRepo.INSTANCE
    private val gameStateRepo: GameStateRepo = GameStateRepo.INSTANCE

     fun clearProfile() {
        currentUserRepo.clearProfile()
    }

     fun getProfile(): CurrentUserProfile? {
        return currentUserRepo.getProfile()
    }

     fun saveProfile(currentUserProfile: CurrentUserProfile) {
        currentUserRepo.saveProfile(currentUserProfile)
    }

     fun createProfile(userProfile: UserProfile): UserProfile {
       return currentUserRepo.createProfile(userProfile)
    }

     fun getProfileByName(username: String): UserProfile? {
       return userProfileRepo.getProfileByName(username)
    }

     fun getProfile(GUID: String): UserProfile? {
        return userProfileRepo.getProfile(GUID)
    }

     fun saveProfile(userProfile: UserProfile) {
        userProfileRepo.saveProfile(userProfile)
    }

     fun createProfile(username: String): UserProfile {
       return userProfileRepo.createProfile(username)
    }

     fun getState(playerGUID: String): GameState? {
       return gameStateRepo.getState(playerGUID)
    }

     fun saveState(gameState: GameState) {
        gameStateRepo.saveState(gameState)
    }

     fun updateState(gameState: GameState) {
        gameStateRepo.updateState(gameState)
    }

     fun createState(playerGUID: String, virusName: String): GameState {
        return gameStateRepo.createState(playerGUID, virusName)
    }

    companion object {
        var INSTANCE : GameRepositoryFacade? = null

        @Synchronized
        fun getInstance(): GameRepositoryFacade
        {
            if(INSTANCE == null)
                INSTANCE = GameRepositoryFacade()
            return INSTANCE!!
        }
    }

}