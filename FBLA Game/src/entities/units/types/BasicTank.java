package entities.units.types;

import entities.core.Coordinate;
import entities.core.Entity;
import entities.projectiles.Flak;
import entities.projectiles.FlakBomb;
import entities.units.Player;
import entities.units.Unit;
import managers.ImageManager;

public class BasicTank extends Unit {
        /*
        The tank is a very beefy unit, with fairly high health and high defense
        The tank will shotgun flak within a certain radius at a very slow rate,and moves very slowly.
        However, it is virtually impossible to knock the tank back, forcing the player to reposition if
        the tank gets too close.
        */
        final private float AverageShotCooldown = 4.5f;
        final private float AttackRadius = 42.5f;
        final private int FlakCount = 25;

        private float shotCooldown; // Specific delay for this unit
        private float lastShot; // Last time this unit shot

        public BasicTank(float x, float y, Entity.Team team) {
            super(x, y, team);

            // Adjusting Rendering Variables
            this.sprite = ImageManager.getImage("PlaceholderShip");
            this.sprite.setImageColor(0f, 1f, 1f);

            this.width = 4.5f;
            this.height = 4.5f;

            // Adjusting Physics Variables
            this.mass = 500f;
            this.thrust = 0.035f;

            // Adjusting Damage Stats
            this.attackDamage = 50;

            // Adjusting Other Stats
            this.maxHealth = 300;
            this.health = maxHealth;

            this.defense = 50;

            // Adjussting Score
            this.score = 25;

            // Adjusting HitBox
            this.hitBox.setWidth(width / 1.15f);
            this.hitBox.setHeight(height / 1.80f);

            // Unit Specific Variables
            this.shotCooldown = AverageShotCooldown + (float) Math.random() - 1.5f;
            this.lastShot = GetTime();
        }

        // Move towards the player, shoot a laser at the player every 2.5 seconds
        protected void unitAI() {
            Player player = game.getPlayer();

            // Move towards the player
            if(player.isMarked()) return; // Stops enemy actions

            // Face and move towards the player
            this.faceEntity(player);
            this.xSpeed -= thrust * (float) Math.cos(this.angle);
            this.ySpeed -= thrust * (float) Math.sin(this.angle);

            if(GetTime() - lastShot > shotCooldown &&
                    Coordinate.distance(position, player.getPosition()) < AttackRadius) {
                for(int i = 0; i < FlakCount; i++) {
                    new Flak(this,
                            player.getPosition(),
                            this.attackDamage
                    );
                }
                lastShot = GetTime();
            }
        }
}
