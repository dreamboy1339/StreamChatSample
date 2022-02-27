package hjw.app.chat.streamchatsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.getstream.sdk.chat.adapter.MessageListItem
import hjw.app.chat.streamchatsample.databinding.AttachmentImgurBinding
import io.getstream.chat.android.client.models.Attachment
import io.getstream.chat.android.ui.message.list.MessageListItemStyle
import io.getstream.chat.android.ui.message.list.adapter.MessageListListenerContainer
import io.getstream.chat.android.ui.message.list.adapter.viewholder.attachment.AttachmentViewFactory

class ImgurAttachmentViewFactory : AttachmentViewFactory() {

    override fun createAttachmentView(
        data: MessageListItem.MessageItem,
        listeners: MessageListListenerContainer?,
        style: MessageListItemStyle,
        parent: ViewGroup,
    ): View {
        val imgurAttachment = data.message.attachments.firstOrNull { it.isImgurAttachment() }
        return when {
            imgurAttachment != null -> createImgurAttachmentView(imgurAttachment, parent)
            else -> super.createAttachmentView(data, listeners, style, parent)
        }
    }

    private fun Attachment.isImgurAttachment(): Boolean = imageUrl?.contains("imgur") == true

    private fun createImgurAttachmentView(imgurAttachment: Attachment, parent: ViewGroup): View {
        val binding = AttachmentImgurBinding
            .inflate(LayoutInflater.from(parent.context), null, false)

        binding.ivMediaThumb.apply {
            shapeAppearanceModel = shapeAppearanceModel
                .toBuilder()
                .setAllCornerSizes(resources.getDimension(io.getstream.chat.android.ui.R.dimen.stream_ui_selected_attachment_corner_radius))
                .build()
            load(imgurAttachment.imageUrl) {
                allowHardware(false)
                crossfade(true)
                placeholder(io.getstream.chat.android.ui.R.drawable.stream_ui_picture_placeholder)
            }
        }

        return binding.root
    }

}
