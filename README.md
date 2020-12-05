# ItemFrameUtils

When developing a server I found that we had a lot of item frames that needed to be made invisible. The default Minecraft command `/execute as @e[type=item_frame,distance=0..4] run data merge entity @s {Invisible:1b}` exists, but that's a) a bit cumbersome, and b) only available to those with op permissions.

So, I made this.

## Commands

There's just the one command: `/setframevisible <true/false> [distance] [limit?]`. The `true/false` is for whether or not you want the nearby frames to be visible or not. `distance` is for how far away you want to change item frames, and `limit` is an optional number of item frames to change, ordered by distance.

### Examples

`/setframevisible false 10` will set all item frames within 10 blocks to invisible.

`/setframevisible true 30 5` will set the nearest 5 item frames within 30 blocks to visible.

## Permissions

Because an important part of this was giving out permissions without op perms, I needed to add permissions. Very handily, I added a single perm for the single command.

`itemframeutils.setframevisible`
