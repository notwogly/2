package bs.controllers;

import bs.entities.VocabEntity;
import bs.models.Vocab;
import bs.repositories.VocabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vocabs")
public class VocabController {
    private final VocabRepository vocabRepository;

    @Autowired
    public VocabController(VocabRepository vocabRepository){
        this.vocabRepository = vocabRepository;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vocab> getAllVocabs(){
        List<VocabEntity> vocabEntityList = null;
        try {
            vocabEntityList = vocabRepository.findAll();
        }
        catch (Exception e){}
        if(vocabEntityList == null)
            return  null;
        List<Vocab> vocabList = new ArrayList<Vocab>();
        for(VocabEntity vocabEntity: vocabEntityList)
        {
            Vocab vocab = new Vocab(vocabEntity);
            vocabList.add(vocab);
        }
        return vocabList;
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addVocab(@RequestBody Vocab vocab){
        VocabEntity vocabEntity = null;
        try{
            vocabEntity = vocabRepository.findByWord(vocab.getWord());
        }catch (Exception e){}
        if(vocabEntity != null)
            vocabRepository.delete(vocabEntity);
        vocabEntity = new VocabEntity(vocab.getWord(),vocab.getInterpretation());
        if(vocab.getExampleSen() != null)
            vocabEntity.setExampleSen(vocab.getExampleSen());
        vocabRepository.save(vocabEntity);
        return ;
    }

}
