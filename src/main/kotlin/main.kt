fun main() {
    val newPost = Post(
        ownerId = 123,
        fromId = 456,
        date = 1650000000,
        text = "Hello, World!"
    )

    val addedPost = WallService.add(newPost)
    println(addedPost)

    val updatedPost = addedPost.copy(text = "Updated post")
    val isUpdated = WallService.update(updatedPost)
    println(isUpdated)
}

data class Comments(
    val count: Int = 0,
    val canPost: Int = 1,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
)

data class Likes(
    val count: Int = 0,
    val userLikes: Int = 0,
    val canLike: Int = 1,
    val canPublish: Int = 1
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Int = 0
)

data class Views(
    val count: Int = 0
)

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 0,
    val placeholder: Any? = null,
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = "all"
)

data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int? = null,
    val date: Int,
    val text: String,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Int = 0,
    val comments: Comments = Comments(),
    val copyright: Copyright? = null,
    val likes: Likes = Likes(),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String = "post",
    val postSource: Any? = null,
    val attachments: List<Any> = emptyList(),
    val geo: Any? = null,
    val signerId: Int? = null,
    val copyHistory: List<Post> = emptyList(),
    val canPin: Int = 0,
    val canDelete: Int = 0,
    val canEdit: Int = 0,
    val isPinned: Int = 0,
    val markedAsAds: Int = 0,
    val isFavorite: Boolean = false,
    val donut: Donut = Donut(),
    val postponedId: Int = 0
)

interface Attachment {
    val type: String
}

abstract class PhotoAttachment(
    override val type: String
) : Attachment {
    abstract val id: Int
    abstract val ownerId: Int


}

class Photo(
    override val id: Int, override val ownerId: Int,
    val albumId: Int,
    val userId: Int,
    val text: String,
    val date: Int,
    val sizes: Sizes = Sizes(),
    val width: Int,
    val height: Int,
) : PhotoAttachment("photo") {
    class Sizes {
        val type: String = ""
        val url: String = ""
        val width: Int = 0
        val height: Int = 0

    }
}


abstract class AttachLinkAttachment(
    override val type: String
) : Attachment {

}

class AttachLink(
    val url: String,
    val title: String,
    val caption: String? = null,
    val description: String,
    val photo: Photo = Photo(1, 1, 1, 1, "", 1, Photo.Sizes(), 1, 1),
    val previewPage: String,
    val previewUrl: String

) : AttachLinkAttachment("attachLink")

abstract class AudioAttachment(
    override val type: String
) : Attachment {
    abstract val id: Int
    abstract val ownerId: Int

}

class Audio(
    override val id: Int,
    override val ownerId: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    val url: String,
    val liricsId: Int? = null,
    val albumId: Int? = null,
    val genreId: Int,
    val date: Int,
    val noSearch: Int? = null,
    val isHd: Int

) : AudioAttachment("audio")

abstract class StickerAttachment(
    override val type: String
) : Attachment {

}

class Sticker(
    val stickerId: Int,
    val innerType: String,
    val isAllowed: Boolean,
    val productId: Int,
    val images: Images = Images(),
    val imagesWithBackground: ImagesWithBackground = ImagesWithBackground(),
    val animationUrl: String
) : StickerAttachment("sticker") {
    class Images {
        val url: String = ""
        val width: Int = 0
        val height: Int = 0
    }

    class ImagesWithBackground {
        val url: String = ""
        val width: Int = 0
        val height: Int = 0
    }
}

abstract class HistoryAttachment(
    override val type: String
) : Attachment {
    abstract val id: Int
    abstract val ownerId: Int

}

class History(
    override val ownerId: Int,
    override val id: Int,
    val expiredAd: Int,
    val isExpired: Boolean,
    val canSee: Boolean,
    val date: Int,
    val seen: Int,
    val isDeleted: Boolean,
    val photo: Photo,
    val video: Video,
    val link: String,
    val parentStoryOwnerId: Int,
    val parentStory: History,
    val replies: Replies = Replies(),
    val canComment: Boolean,
    val canShare: Boolean,
    val canReply: Boolean,
    val clickableStickers: ClickableStickers = ClickableStickers(),
    val views: Int,
    val accessKey: String


) : HistoryAttachment("history") {
    class Video {
        val first_frame_800: String = ""
        val first_frame_320: String = ""
        val first_frame_160: String = ""
        val first_frame_130: String = ""
        val is_private: Boolean = false
    }

    class Replies {
        val count: Int = 0
        val new: Int = 0
    }

    class ClickableStickers {

    }
}

object WallService {
    private var posts = emptyArray<Post>()
    private var nextId = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextId)
        posts += newPost
        nextId++
        return newPost
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = post
                return true
            }
        }
        return false
    }
}
