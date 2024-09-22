
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
    val donut: Donut = Donut()
)

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
