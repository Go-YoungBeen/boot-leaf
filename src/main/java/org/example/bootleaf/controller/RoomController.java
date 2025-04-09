package org.example.bootleaf.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootleaf.model.entity.Message;
import org.example.bootleaf.model.entity.Reply;
import org.example.bootleaf.model.entity.Room;
import org.example.bootleaf.model.repository.MessageRepository;
import org.example.bootleaf.model.repository.ReplyRepository;
import org.example.bootleaf.model.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RoomController {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final ReplyRepository replyRepository;


    @GetMapping("/")
    public String index(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "index";
    }

    @PostMapping("/rooms")
    public String createRoom(@RequestParam("title") String title, RedirectAttributes redirectAttributes) {
        if (roomRepository.existsByTitle(title)) {
            redirectAttributes.addFlashAttribute("error", "이미 존재하는 방 이름입니다.");
        } else {
            Room room = new Room();
            room.setTitle(title);
            roomRepository.save(room);
            redirectAttributes.addFlashAttribute("message", "방 생성 완료: " + title);
        }
        return "redirect:/";
    }

    @GetMapping("/rooms/{roomId}")
    public String enterRoom(@PathVariable Long roomId, Model model) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
        List<Message> messages = messageRepository.findByRoomOrderByCreatedAtDesc(room);

        Map<Long, List<Reply>> repliesByMessage = new HashMap<>();
        for (Message message : messages) {
            List<Reply> replies = replyRepository.findByMessageOrderByCreatedAtAsc(message);
            repliesByMessage.put(message.getId(), replies);
        }

        model.addAttribute("room", room);
        model.addAttribute("messages", messages);
        model.addAttribute("repliesByMessage", repliesByMessage);
        return "room";
    }




    @PostMapping("/rooms/{roomId}/messages")
    public String postMessage(@PathVariable Long roomId,
                              @RequestParam String content,
                              RedirectAttributes redirectAttributes) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
        Message message = new Message();
        message.setContent(content);
        message.setRoom(room);
        messageRepository.save(message);

        return "redirect:/rooms/" + roomId;
    }

    @PostMapping("/rooms/{id}/delete")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 방이 없습니다."));

        roomRepository.delete(room);
        redirectAttributes.addFlashAttribute("message", "방이 삭제되었습니다.");

        return "redirect:/";
    }

    @PostMapping("/rooms/{roomId}/messages/{messageId}/replies")
    public String postReply(@PathVariable Long roomId,
                            @PathVariable Long messageId,
                            @RequestParam String content) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        Reply reply = new Reply();
        reply.setMessage(message);
        reply.setContent(content);
        replyRepository.save(reply);

        return "redirect:/rooms/" + roomId;
    }




}
